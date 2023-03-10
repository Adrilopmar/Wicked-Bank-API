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
import com.ironhack.wickedbank.wickedbank.repository.*;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    SavingRepository savingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Savings createSaving(SavingsDto dto, Authentication authentication) {
        authentication(authentication);
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
        savingRepository.save(account);
        return account;
    }

    public Account createChecking(CheckingDto dto,Authentication authentication) {
        authentication(authentication);
        Checking account = new Checking(); // create new checking
        List<User> owners =new ArrayList(); // create list for owners
        List<Account> accountList = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(dto.getOwnerId());
        if(checkUserInDatabase(dto.getOwnerId())){
            owners.add(optionalUser.get());
        }
        if (dto.getSecondaryOwner() != null){
            Optional<User> optionalSecondOwner = userRepository.findById(dto.getSecondaryOwner());
            if(checkUserInDatabase(optionalSecondOwner.get().getUserId())){
                owners.add(optionalSecondOwner.get());
            }
        }
        if (optionalUser.get().getRoles().toString().contains("ACCOUNT")){
            Optional<AccountHolder> optionalHolder = accountHolderRepository.findById(dto.getOwnerId());
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
                return studentChecking;
        } else {
            accountList.add(account);
            optionalHolder.get().setAccounts(accountList);
            }
        } else if (optionalUser.get().getRoles().toString().contains("THIRD")) {
            Optional<ThirdParty> optionalThirdParty = thirdPartyRepository.findById(dto.getOwnerId());
            optionalThirdParty.get().setAccounts(accountList);
        }
        if (dto.getBalance() != null) { //setting balance & checking minimum
            if (dto.getBalance().getAmount().compareTo(new BigDecimal("250")) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance can not be less than 250");
            } else {
                account.setBalance(dto.getBalance());
            }
            account.setType(Type.CHECKING);
            account.setOwners(owners);
            account.setOwnerId(dto.getOwnerId());
            account.setSecretKey(dto.getSecretKey());
            checkingRepository.save(account);
            return account;
        }else return null;
    }
    public CreditCard createCreditCard(CreditCardDto dto,Authentication authentication) {
        authentication(authentication);
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
        creditCardRepository.save(account);
        return account;
    }
    public Admin createAdmin(AdminDto dto,Authentication authentication) {
//        authentication(authentication);
        Role adminRole = new Role("ADMIN");
        Admin admin = new Admin(
                dto.getName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                List.of(adminRole));
        userRepository.save(admin);
        return admin;
    }
    public AccountHolder createAccountHolder(AccountHolderDto dto,Authentication authentication) {
        authentication(authentication);
        Role role = new Role("ACCOUNT_HOLDER");
        AccountHolder accountHolder = new AccountHolder(
                dto.getName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getUsername(),
                List.of(role));
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

    public ThirdParty createThirdParty(ThirdPartyDto dto,Authentication authentication) {
        authentication(authentication);
        Role role = new Role("THIRD_PARTY");
       ThirdParty thirdParty = new ThirdParty(dto.getName(),
               dto.getUsername(),
               passwordEncoder.encode(dto.getPassword()),
               List.of(role),
               dto.getHashedKey());
       userRepository.save(thirdParty);
       return thirdParty;
    }

    public void deleteUser(Long userId, DeleteDto dto,Authentication authentication) {
        authentication(authentication);
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
    public void deleteAccount(Long accountId, DeleteDto dto,Authentication authentication) {
        authentication(authentication);
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
    public void authentication(Authentication authentication){
        if(authentication == null ||
                !SecurityContextHolder.getContext().getAuthentication().getName().equals(authentication.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied");
        }
    }
}