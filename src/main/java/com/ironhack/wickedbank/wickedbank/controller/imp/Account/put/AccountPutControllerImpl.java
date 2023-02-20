package com.ironhack.wickedbank.wickedbank.controller.imp.Account.put;

import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update.AccountHolderPutDto;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountPutControllerImpl {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserService userService;

    @PutMapping("/account-holder/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder editAllAccountHolder(
            @RequestParam Long userId,
            @RequestBody @Valid AccountHolderPutDto accountHolderPutDto){
        return userService.editAllAccountHolder(userId,accountHolderPutDto);
    }
}
