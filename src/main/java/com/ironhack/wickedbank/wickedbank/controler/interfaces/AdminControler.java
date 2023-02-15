package com.ironhack.wickedbank.wickedbank.controler.interfaces;

import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;

public interface AdminControler {
    Savings createSaving(SavingsDto savingsDto);
    void createChecking(CheckingDto checkingDto);
}
