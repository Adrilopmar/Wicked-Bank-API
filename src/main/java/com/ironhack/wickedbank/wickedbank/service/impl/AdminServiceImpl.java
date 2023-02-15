package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.enums.Type;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.accountType.Checking;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.accountType.StudentChecking;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.repository.AccountHolderRepository;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    UserRepository userRepository;

    public Savings createSaving(SavingsDto dto) {
        Savings account= new Savings(); // create new saving account
        List<User> owners =new ArrayList<>(); // create list for owners
        List<Account> accountList = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(dto.getOwnerId());
        if (optionalUser.isPresent()){ //check if user exist in db
            owners.add(optionalUser.get());
            accountList.add(account);
           optionalUser.get().setAccounts(accountList);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
        if(dto.getBalance() != null){ //setting balance & checking minimum
            if (dto.getBalance().getAmount().compareTo(new BigDecimal("100"))<0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance can not be less than 100");
            }else {
                account.setBalance(dto.getBalance());
            }
        }else {
            account.setBalance(new Money( new BigDecimal("1000")));
        }
        if (dto.getSecondaryOwner() != null){
            Optional<User> optionalSecondOwner = userRepository.findById(dto.getSecondaryOwner());
            if (optionalSecondOwner.isPresent()){
                owners.add(optionalSecondOwner.get());
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary owner not found");
            }
        }
        if (dto.getInterestRate() != null){
            account.setInterestRate(dto.getInterestRate());
        }
        account.setType(Type.SAVINGS);
        account.setOwners(owners);
        account.setOwner(dto.getOwnerId());
        account.setSecretKey(dto.getSecretKey()); // encrypt needed
//        accountList.add(account);
//        optionalUser.get().setAccounts(accountList);
        accountRepository.save(account);
        return account;
    }

    public void createChecking(CheckingDto dto) {
        Checking account = new Checking(); // create new checking
        List<User> owners =new ArrayList(); // create list for owners
        List<Account> accountList = new ArrayList<>();
        Optional<AccountHolder> optionalHolder = accountHolderRepository.findById(dto.getOwnerId());
        if (optionalHolder.isPresent()){ //check if user exist in db
            owners.add(optionalHolder.get());
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
        if (dto.getSecondaryOwner() != null){
            Optional<User> optionalSecondOwner = userRepository.findById(dto.getSecondaryOwner());
            if (optionalSecondOwner.isPresent()){
                owners.add(optionalSecondOwner.get());
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary owner not found");
            }
        }
        if(account.getCreationDate().getYear()-optionalHolder.get().getDateOfBirth().getYear() <24){
            System.out.println("creating student checking due to age");
            StudentChecking studentChecking = new StudentChecking();
            accountList.add(studentChecking);
            optionalHolder.get().setAccounts(accountList);
            if(dto.getBalance() != null){ //setting balance & checking minimum
                studentChecking.setBalance(dto.getBalance());
            }else {
                studentChecking.setBalance(new Money(new BigDecimal("0.00")));
            }
            studentChecking.setType(Type.STUDENT_CHECKING);
            studentChecking.setOwners(owners);
            studentChecking.setOwner(dto.getOwnerId());
            studentChecking.setSecretKey(dto.getSecretKey());
            accountRepository.save(studentChecking);
        }else {
            accountList.add(account);
            optionalHolder.get().setAccounts(accountList);
            if(dto.getBalance() != null){ //setting balance & checking minimum
                if (dto.getBalance().getAmount().compareTo(new BigDecimal("250"))<0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance can not be less than 250");
                }else {
                    account.setBalance(dto.getBalance());
                }
            }
            account.setType(Type.CHECKING);
            account.setOwners(owners);
            account.setOwner(dto.getOwnerId());
            account.setSecretKey(dto.getSecretKey());
            accountRepository.save(account);
        }
    }
}
