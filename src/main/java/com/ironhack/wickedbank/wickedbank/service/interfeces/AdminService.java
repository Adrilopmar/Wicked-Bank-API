package com.ironhack.wickedbank.wickedbank.service.interfeces;

import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
public interface AdminService {
    Savings createSaving(SavingsDto savingsDto);
    void createChecking(CheckingDto checkingDto);
}
