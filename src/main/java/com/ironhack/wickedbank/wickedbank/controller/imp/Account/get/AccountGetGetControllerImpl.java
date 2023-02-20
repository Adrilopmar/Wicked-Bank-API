package com.ironhack.wickedbank.wickedbank.controller.imp.Account.get;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controller.interfaces.account.AccountGetController;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/my-account/{accountId}")
    public Account getUserAccountById(@PathVariable Long accountId, Authentication authentication){
        return accountService.getAccountById(accountId, authentication);}
    @GetMapping("/my-account/{accountId}/balance")
    public Money getUserAccountBalanceById(@PathVariable Long accountId, Authentication authentication){
        return accountService.getUserAccountBalanceById(accountId, authentication);}
}
