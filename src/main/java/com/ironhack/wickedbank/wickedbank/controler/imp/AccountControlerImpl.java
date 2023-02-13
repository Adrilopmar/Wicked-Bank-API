package com.ironhack.wickedbank.wickedbank.controler.imp;

import com.ironhack.wickedbank.wickedbank.controler.interfaces.AccountControler;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountControlerImpl implements AccountControler {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){return accountRepository.findAll();}
}
