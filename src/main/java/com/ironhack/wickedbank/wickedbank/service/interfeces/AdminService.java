package com.ironhack.wickedbank.wickedbank.service.interfeces;

import com.ironhack.wickedbank.wickedbank.controler.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;

public interface AdminService {
    Savings createSaving(SavingsDto savingsDto);
    void createChecking(CheckingDto checkingDto);
    CreditCard createCreditCard(CreditCardDto creditCardDto);
    Admin createAdmin(AdminDto adminDto);
    AccountHolder createAccountHolder(AccountHolderDto accountHolderDto);
    ThirdParty createThirdParty(ThirdPartyDto thirdPartyDto);
}
