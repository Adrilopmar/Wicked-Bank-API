package com.ironhack.wickedbank.wickedbank.controller.interfaces.admin;

import com.ironhack.wickedbank.wickedbank.controller.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;

public interface AdminControler {
    Savings createSaving(SavingsDto savingsDto);
    Account createChecking(CheckingDto checkingDto);
    CreditCard createCreditCard(CreditCardDto creditCardDto);
}
