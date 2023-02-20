package com.ironhack.wickedbank.wickedbank.controller.interfaces.account;

import com.ironhack.wickedbank.wickedbank.model.Account;
import org.springframework.security.core.Authentication;

public interface AccountGetController {
    Account getUserAccountById(Long accountId, Authentication authentication);
}
