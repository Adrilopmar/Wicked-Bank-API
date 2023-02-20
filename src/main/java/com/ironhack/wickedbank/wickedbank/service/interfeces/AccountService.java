package com.ironhack.wickedbank.wickedbank.service.interfeces;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.model.Account;
import org.springframework.security.core.Authentication;

public interface AccountService {
    Account getAccountById(Long id, Authentication authentication);
    Money getUserAccountBalanceById(Long accountId, Authentication authentication);
}
