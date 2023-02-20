package com.ironhack.wickedbank.wickedbank.service.impl;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.SavingRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AccountService;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SavingRepository savingRepository;
    @Autowired
    AdminService adminService;

    public Account getAccountById(Long id, Authentication authentication) {
        adminService.authentication(authentication);
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");
        }else{
            return optionalAccount.get();
        }
    }
    public Money getUserAccountBalanceById(Long accountId, Authentication authentication){
        return getAccountById(accountId,authentication).getBalance();
    }

}
