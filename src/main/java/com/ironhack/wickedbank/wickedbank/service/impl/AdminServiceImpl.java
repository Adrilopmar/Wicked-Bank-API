package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controller.dto.DeleteDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.enums.Type;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.Role;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;

    public Savings createSaving(SavingsDto dto) {
//        Optional<User> ac = userRepository.findByName(username);
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
        account.setOwnerId(dto.getOwnerId());
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
            studentChecking.setOwnerId(dto.getOwnerId());
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
            account.setOwnerId(dto.getOwnerId());
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
            }else {
                account.setBalance(dto.getBalance());
            }
        } else {
            account.setBalance(new Money( new BigDecimal("0")));
        }
        if(dto.getInterestRate()!= null){
            account.setInterestRate(dto.getInterestRate());
        }
        account.setType(Type.CREDIT_CARD);
        account.setOwners(owners);
        account.setOwnerId(dto.getOwnerId());
        account.setSecretKey(dto.getSecretKey());
        accountRepository.save(account);
        return account;
    }
    public Admin createAdmin(AdminDto dto) {
        Admin admin = new Admin();
        Role adminRole = new Role("ADMIN");
        admin.setRoles(List.of(adminRole));
        admin.setName(dto.getName());
        admin.setUsername(dto.getUsername());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(admin);
        return admin;
    }
    public AccountHolder createAccountHolder(AccountHolderDto dto) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName(dto.getName());
        accountHolder.setPassword(passwordEncoder.encode(dto.getPassword()));
        accountHolder.setUsername(dto.getUsername());
        Role role = new Role("ACCOUNT_HOLDER");
        accountHolder.setRoles(List.of(role));
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
        Role role = new Role("THIRD_PARTY");
        thirdParty.setRoles(List.of(role));
        thirdParty.setHashedKey(dto.getHashedKey());
        thirdParty.setPassword(passwordEncoder.encode(dto.getPassword()));
        thirdParty.setName(dto.getName());
       userRepository.save(thirdParty);
       return thirdParty;
    }

    public void deleteUser(Long userId, DeleteDto dto) {
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findByName(dto.getName()));
        if (optionalAdmin.isPresent()){
            if(optionalAdmin.get().getPassword().equals(dto.getPassword())){
                if(checkUserInDatabase(userId)){
                    userRepository.delete(userRepository.findById(userId).get());
                }
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid credentials");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid credentials");
        }
    }
    public void deleteAccount(Long accountId, DeleteDto dto) {
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findByName(dto.getName()));
        if (optionalAdmin.isPresent()){
            if(optionalAdmin.get().getPassword().equals(dto.getPassword())){
                if(checkAccountInDatabase(accountId)){
                    accountRepository.delete(accountRepository.findById(accountId).get());
                }
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid credentials");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid credentials");
        }
    }
    public Boolean checkUserInDatabase(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){ //check if user exist in db
            return true;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
    public Boolean checkAccountInDatabase(Long accountId){
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()){ //check if user exist in db
            return true;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
        }
    }
}