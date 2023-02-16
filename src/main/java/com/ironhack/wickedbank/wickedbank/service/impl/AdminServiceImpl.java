package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controler.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.enums.Role;
import com.ironhack.wickedbank.wickedbank.enums.Type;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.accountType.Checking;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.accountType.StudentChecking;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountHolderRepository;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.AdminRepository;
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
    AdminRepository adminRepository;
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
        if(checkUserInDatabase(dto.getOwnerId())){
            owners.add(optionalHolder.get());
        }
//        if (optionalHolder.isPresent()){ //check if user exist in db
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
//        }
        if (dto.getSecondaryOwner() != null){
            Optional<User> optionalSecondOwner = userRepository.findById(dto.getSecondaryOwner());
//            if (optionalSecondOwner.isPresent()){
//                owners.add(optionalSecondOwner.get());
//            }else {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary owner not found");
//            }
            if(checkUserInDatabase(optionalSecondOwner.get().getUserId())){
                owners.add(optionalSecondOwner.get());
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
    public CreditCard createCreditCard(CreditCardDto dto) {
        CreditCard account = new CreditCard(); // create new credit card
        List<User> owners =new ArrayList(); // create list for owners
        List<Account> accountList = new ArrayList<>();
        Optional<AccountHolder> optionalHolder = accountHolderRepository.findById(dto.getOwnerId());
        if(checkUserInDatabase(dto.getOwnerId())){
            owners.add(optionalHolder.get());
        }
        if (dto.getSecondaryOwner() != null){
            Optional<User> optionalSecondOwner = userRepository.findById(dto.getSecondaryOwner());
            if(checkUserInDatabase(optionalSecondOwner.get().getUserId())){
                owners.add(optionalSecondOwner.get());
            }
        }
        accountList.add(account);
        optionalHolder.get().setAccounts(accountList);
        if(dto.getBalance() != null){ //setting balance & checking minimum
            if (dto.getBalance().getAmount().compareTo(new BigDecimal("100"))<0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance can not be less than 100");
            } else if (dto.getBalance().getAmount().compareTo(new BigDecimal("100000"))>0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance can not be higher than 100000");
            } else {
                account.setBalance(dto.getBalance());
            }
        }
        account.setType(Type.CREDIT_CARD);
        account.setOwners(owners);
        account.setOwner(dto.getOwnerId());
        account.setSecretKey(dto.getSecretKey());
        accountRepository.save(account);
        return account;
    }
    public Admin createAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findAllByRole(Role.ADMIN));
        if (!optionalAdmin.isPresent()){
            admin.setName(adminDto.getName());
            admin.setPassword(adminDto.getPassword());
            userRepository.save(admin);
            return admin;
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This application only supports 1 admin.");
        }
    }
    public AccountHolder createAccountHolder(AccountHolderDto dto) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName(dto.getName());
        accountHolder.setPassword(dto.getPassword());
        if (dto.getAddress()!= null){
            accountHolder.setAddress(dto.getAddress());
        }
        if (dto.getDateOfBirth()!=null){
            accountHolder.setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getMailingAddress()!=null){
            accountHolder.setMailingAddress(dto.getMailingAddress());
        }
        userRepository.save(accountHolder);
        return accountHolder;
    }

    public ThirdParty createThirdParty(ThirdPartyDto dto) {
       ThirdParty thirdParty = new ThirdParty();
       thirdParty.setHashedKey(dto.getHashedKey());
       thirdParty.setPassword(dto.getPassword());
       thirdParty.setName(dto.getName());
       userRepository.save(thirdParty);
       return thirdParty;
    }

    public Boolean checkUserInDatabase(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){ //check if user exist in db
            return true;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
}