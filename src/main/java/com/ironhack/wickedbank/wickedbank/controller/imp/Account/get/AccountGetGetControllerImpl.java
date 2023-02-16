package com.ironhack.wickedbank.wickedbank.controller.imp.Account.get;

import com.ironhack.wickedbank.wickedbank.controller.interfaces.account.AccountGetController;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountGetGetControllerImpl implements AccountGetController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAllAccounts(){return accountRepository.findAll();}
    @GetMapping("/{accountId}")
    public Account getAccountById(@PathVariable Long accountId){
        return accountService.getAccountById(accountId);}
}
