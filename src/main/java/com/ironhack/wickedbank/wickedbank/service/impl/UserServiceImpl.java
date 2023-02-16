package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update.AccountHolderPutDto;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
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
}
