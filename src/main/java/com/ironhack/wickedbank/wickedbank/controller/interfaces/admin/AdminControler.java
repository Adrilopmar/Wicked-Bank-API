package com.ironhack.wickedbank.wickedbank.controller.interfaces.admin;

import com.ironhack.wickedbank.wickedbank.controller.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import org.springframework.security.core.Authentication;

public interface AdminControler {
    Savings createSaving(SavingsDto savingsDto, Authentication authentication);
    Account createChecking(CheckingDto checkingDto,Authentication authentication);
    CreditCard createCreditCard(CreditCardDto creditCardDto,Authentication authentication);
}
