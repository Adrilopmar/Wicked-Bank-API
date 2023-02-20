package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update.AccountHolderPutDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.enums.Type;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.Transaction;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.ThirdPartyRepository;
import com.ironhack.wickedbank.wickedbank.repository.TransactionRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AdminService adminService;

    public User findUserById(Long id,Authentication authentication) {
        adminService.authentication(authentication);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
    public AccountHolder editAllAccountHolder(Long userId, AccountHolderPutDto dto) {
//        Optional<AccountHolder> optionalHolder = userRepository.findById(userId);
//        if(optionalHolder.isPresent()){
//            optionalHolder.get().setUserId(userId);
//            optionalHolder.get();
//
//        }
        return null;
    }

    public Transaction newTransaction(Long senderId,
                                      Long receiverAccountId,
                                      String primaryOwner,
                                      String secondaryOwner,
                                      transactionDto dto,
                                      Authentication authentication) {
        adminService.authentication(authentication);
        if(senderId.equals(receiverAccountId)){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"It is not allowed to send " +
                    "money from one account to the same account.");
        }
        Optional<User> optionalUser = userRepository.findById(senderId);
        Optional<Account> optionalSenderAccount = accountRepository.findById(dto.getSenderAccountId());
        Optional<Account> optionalReceiverAccount = accountRepository.findById(receiverAccountId);
        if (checkSenderReceiverFounds(optionalReceiverAccount,optionalReceiverAccount,dto.getAmount())){
            return acceptedTransaction(optionalUser.get(),optionalSenderAccount.get(),optionalReceiverAccount.get(),dto);
        }
        return null;
    }
    public Transaction thirdPartyTransaction(Long senderId,
                                             Long receiverId,
                                             String secretKey ,
                                             transactionDto dto,
                                             HttpServletRequest header,
                                             Authentication authentication){
        adminService.authentication(authentication);
        Optional<ThirdParty> optionalUser = thirdPartyRepository.findById(senderId);
        Optional<Account> optionalSenderAccount = accountRepository.findById(dto.getSenderAccountId());
        Optional<Account> optionalReceiverAccount = accountRepository.findById(receiverId);
        String hashedKey = header.getHeader("hashed-key");
        if(!optionalUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        } else if (!optionalUser.get().getHashedKey().equals(hashedKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Incorrect hashKey");
        }
        if (checkSenderReceiverFounds(optionalSenderAccount,optionalReceiverAccount,dto.getAmount())) {
            boolean isUsersAccount = false;
            for (User user : optionalSenderAccount.get().getOwners()) {
                if (user.getUserId().equals(senderId)) {
                    isUsersAccount = true;
                }
            }
            if (!isUsersAccount){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Illegal transaction");
            }else {
                return acceptedTransaction(optionalUser.get(),optionalSenderAccount.get(),optionalReceiverAccount.get(),dto);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Something went wrong. Try again in few minutes. Thanks");
        }
    }
    public boolean checkSenderReceiverFounds(Optional<Account> sender, Optional<Account> receiver, Money transferAmount){
        if(!sender.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Sender not found");
        }else if (!receiver.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Receiver Account not found");
        }else if(transferAmount.getAmount().compareTo(sender.get().getBalance().getAmount())>0){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Insufficient founds");
        } else if (transferAmount.getAmount().compareTo(new BigDecimal("0"))<=0) {
            sender.get().getBalance().decreaseAmount(sender.get().getBalance().getAmount().divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN));
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,"Bobo o que? You can't do this kind of transaction moron. You have been penalized with 50% of transaction amount");
        } else {return true;}
    }
    public Transaction acceptedTransaction(User senderUser,Account sender,Account receiver, transactionDto dto){
        boolean before = isRedNumbers(sender.getBalance(),sender.getType());
        sender.setBalance( new Money(
                sender.getBalance().decreaseAmount(dto.getAmount())
        ));
        boolean after = isRedNumbers(sender.getBalance(),sender.getType());
        if (!before && after){
            sender.getBalance().decreaseAmount(new BigDecimal("40"));
        }
        accountRepository.save(sender);
        receiver.setBalance(new Money(
                receiver.getBalance().increaseAmount(dto.getAmount())
        ));
        accountRepository.save(receiver);
        Transaction transaction =new Transaction(
                senderUser,
                sender.getAccountId(),
                receiver.getOwnerId(),
                receiver.getAccountId(),
                dto.getAmount());
        senderUser.setTransactions(transaction);
        transactionRepository.save(transaction);
        return transaction;
    }
    public  boolean isRedNumbers(Money senderBalance, Type accountType){
        boolean redNumbers =false;
        switch (accountType) {
            case CHECKING -> {
                if (senderBalance.getAmount().compareTo(new BigDecimal("250.00")) < 0) redNumbers = true;
            }
            case SAVINGS -> {
                if (senderBalance.getAmount().compareTo(new BigDecimal("1000.00")) < 0) redNumbers = true;
            }
            default -> {}
        }
        return  redNumbers;
    }

}
