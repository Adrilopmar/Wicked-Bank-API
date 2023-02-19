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

public interface AdminService {
    Savings createSaving(SavingsDto savingsDto);//String username
    Account createChecking(CheckingDto checkingDto);
    CreditCard createCreditCard(CreditCardDto creditCardDto);
    Admin createAdmin(AdminDto adminDto);
    AccountHolder createAccountHolder(AccountHolderDto accountHolderDto);
    ThirdParty createThirdParty(ThirdPartyDto thirdPartyDto);
    void deleteUser(Long userId, DeleteDto deleteDto);
    void deleteAccount(Long accountId, DeleteDto deleteDto);
}
