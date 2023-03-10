package com.ironhack.wickedbank.wickedbank.controller.imp.user.post;

import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.controller.interfaces.user.UserControler;
import com.ironhack.wickedbank.wickedbank.model.Transaction;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserControler {
    @Autowired
    UserService userService;
    @PostMapping("/{senderId}/transaction/{receiverId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction newTransaction(@PathVariable (name = "senderId") Long senderId,
                                      @PathVariable (name = "receiverId") Long receiverId,
                                      @RequestParam (name = "primary-owner") String primaryOwner,
                                      @RequestParam (name = "secondary-owner", required = false)String secondaryOwner,
                                      @RequestBody @Valid transactionDto transactionDto,
                                      Authentication authentication){
        return userService.newTransaction(senderId,receiverId,primaryOwner,secondaryOwner,transactionDto,authentication);
    }
    @PostMapping("/third-party/{senderId}/transaction/{receiverAccountId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction thirdPartyTransaction(
            @PathVariable (name = "senderId") Long senderId,
            @PathVariable (name = "receiverAccountId") Long receiverAccountId,
            @RequestParam (name = "secret-key") String secretKey,
            @RequestBody @Valid transactionDto transactionDto,
            HttpServletRequest headers,
            Authentication authentication
            ){
        return userService.thirdPartyTransaction(senderId,receiverAccountId,secretKey,transactionDto,headers,authentication);
    }
}
