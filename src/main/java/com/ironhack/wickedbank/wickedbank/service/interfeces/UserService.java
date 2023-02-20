package com.ironhack.wickedbank.wickedbank.service.interfeces;

import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update.AccountHolderPutDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.model.Transaction;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface UserService {
    User findUserById(Long id, Authentication authentication);
    AccountHolder editAllAccountHolder(Long userId, AccountHolderPutDto accountHolderPutDto);

    Transaction newTransaction(Long senderId, Long receiverId,String primaryOwner, String secondaryOwner, transactionDto transactionDto,Authentication authentication);
    Transaction thirdPartyTransaction(Long senderId, Long receiverId, String secretKey, transactionDto dto, HttpServletRequest header,Authentication authentication);
}
