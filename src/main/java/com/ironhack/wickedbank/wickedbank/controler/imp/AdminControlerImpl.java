package com.ironhack.wickedbank.wickedbank.controler.imp;

import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controler.interfaces.AdminControler;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminControlerImpl implements AdminControler {
    @Autowired
    AdminService adminService;

    @PostMapping("/admin/new/saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createSaving(@RequestBody @Valid SavingsDto savingsDto){
       return adminService.createSaving(savingsDto);
    }
    @PostMapping("/admin/new/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChecking(@RequestBody @Valid CheckingDto checkingDto){
       adminService.createChecking(checkingDto);
    }
}
