package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account getAccountById(Long id, Authentication authentication) {
        if(authentication == null ||
                !SecurityContextHolder.getContext().getAuthentication().getName().equals(authentication.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied");
        }
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
        }else{
            return optionalAccount.get();
        }
    }

}
