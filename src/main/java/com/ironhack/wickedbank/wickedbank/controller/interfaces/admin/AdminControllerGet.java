package com.ironhack.wickedbank.wickedbank.controller.interfaces.admin;

import com.ironhack.wickedbank.wickedbank.model.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AdminControllerGet {
    Account getAccountById(Long accountId, Authentication authentication);
     List<Account> getAllAccounts(Authentication authentication);
}
