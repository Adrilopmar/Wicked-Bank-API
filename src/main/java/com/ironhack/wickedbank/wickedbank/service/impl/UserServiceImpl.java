package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update.AccountHolderPutDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.Transaction;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.TransactionRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public User findUserById(Long id) {
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
    @Override
    public Transaction newTransaction(Long senderId, Long receiverId, transactionDto dto) {
        // check for authentication if users is allowed to do the move with the account
        // is sender id and user logged id the same?
        // do this logged user owns the dto account sender id?
        if(dto.getSenderAccountId().equals(dto.getReceiverAccountId())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"It is not allowed to send " +
                    "money from one account to the same account.");
        }
        Optional<User> optionalUser = userRepository.findById(senderId);
        if(!optionalUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Receiver not found");
        }
        Optional<User> optionalReceiverUser = userRepository.findById(receiverId);
        if(!optionalUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Receiver not found");
        }
        Optional<Account> optionalSenderAccount = accountRepository.findById(dto.getSenderAccountId());
        Optional<Account> optionalReceiverAccount = accountRepository.findById(dto.getReceiverAccountId());
        if (!optionalSenderAccount.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Sender Account not found");
        } else if (!optionalReceiverAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Receiver Account not found");
        }
        optionalSenderAccount.get().setBalance( new Money(
                optionalSenderAccount.get().getBalance().decreaseAmount(dto.getAmount())
        ));
        accountRepository.save(optionalSenderAccount.get());

        optionalReceiverAccount.get().setBalance(new Money(
                optionalReceiverAccount.get().getBalance().increaseAmount(dto.getAmount())
        ));
        accountRepository.save(optionalReceiverAccount.get());
        Transaction transaction =new Transaction(
                optionalUser.get(),
                dto.getSenderAccountId(),
                receiverId,
                dto.getReceiverAccountId(),
//                userRepository.findById(optionalReceiverAccount.get().getOwnerId()).get().getName(),
                dto.getAmount());

        transactionRepository.save(transaction);
        return transaction;
    }
}
