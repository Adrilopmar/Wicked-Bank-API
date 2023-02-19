package com.ironhack.wickedbank.wickedbank.controller.imp.admin.get;

import com.ironhack.wickedbank.wickedbank.controller.interfaces.admin.AdminControllerGet;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AccountService;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminGetControllerImpl implements AdminControllerGet {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    // =================================== Accounts ==========================
    @GetMapping("/accounts/all")
    public List<Account> getAllAccounts(){return accountRepository.findAll();}
    @GetMapping("/accounts/{accountId}")
    public Account getAccountById(@PathVariable Long accountId, Authentication authentication){
        return accountService.getAccountById(accountId, authentication);}
    // =================================== Users ==========================
    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.findUserById(userId);
    }
}