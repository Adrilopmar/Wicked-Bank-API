package com.ironhack.wickedbank.wickedbank.service.interfeces;

import com.ironhack.wickedbank.wickedbank.controller.dto.DeleteDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import org.springframework.security.core.Authentication;

public interface AdminService {
    Savings createSaving(SavingsDto savingsDto, Authentication authentication);//String username
    Account createChecking(CheckingDto checkingDto,Authentication authentication);
    CreditCard createCreditCard(CreditCardDto creditCardDto,Authentication authentication);
    Admin createAdmin(AdminDto adminDto,Authentication authentication);
    AccountHolder createAccountHolder(AccountHolderDto accountHolderDto,Authentication authentication);
    ThirdParty createThirdParty(ThirdPartyDto thirdPartyDto,Authentication authentication);
    void deleteUser(Long userId, DeleteDto deleteDto,Authentication authentication);
    void deleteAccount(Long accountId, DeleteDto deleteDto,Authentication authentication);
    void authentication(Authentication authentication);
}
