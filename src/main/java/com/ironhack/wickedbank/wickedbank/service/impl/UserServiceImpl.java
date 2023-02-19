package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update.AccountHolderPutDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.Transaction;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.ThirdPartyRepository;
import com.ironhack.wickedbank.wickedbank.repository.TransactionRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    ThirdPartyRepository thirdPartyRepository;
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

    public Transaction newTransaction(Long senderId, Long receiverAccountId,String primaryOwner, String secondaryOwner, transactionDto dto) {
        // check for authentication if users is allowed to do the move with the account
        // is sender id and user logged id the same?
        // do this logged user owns the dto account sender id?
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
    public Transaction thirdPartyTransaction(Long senderId, Long receiverId,String secretKey ,transactionDto dto, HttpServletRequest header){
        // check for authentication if users is allowed to do the move with the account
        // is sender id and user logged id the same?
        // do this logged user owns the dto account sender id?
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
        }else {return true;}
    }
    public Transaction acceptedTransaction(User senderUser,Account sender,Account receiver, transactionDto dto){
        sender.setBalance( new Money(
                sender.getBalance().decreaseAmount(dto.getAmount())
        ));
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
}
