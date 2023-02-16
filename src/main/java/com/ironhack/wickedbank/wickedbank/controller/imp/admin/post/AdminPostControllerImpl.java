package com.ironhack.wickedbank.wickedbank.controller.imp.admin.post;

import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.controller.interfaces.admin.AdminControler;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/new")
public class AdminPostControllerImpl implements AdminControler {
    @Autowired
    AdminService adminService;

    @PostMapping("/saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createSaving(@RequestBody @Valid SavingsDto savingsDto){//, Authentication authentication
       return adminService.createSaving(savingsDto);//, authentication.getName()
    }
    @PostMapping("/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChecking(@RequestBody @Valid CheckingDto checkingDto){
       adminService.createChecking(checkingDto);
    }

    @PostMapping("/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCard(@RequestBody @Valid CreditCardDto creditCardDto) {
        return adminService.createCreditCard(creditCardDto);
    }
    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin AddAdmin(@RequestBody @Valid AdminDto adminDto) {
        return adminService.createAdmin(adminDto);
    }
    @PostMapping("/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolderDto accountHolderDto) {
        return adminService.createAccountHolder(accountHolderDto);
    }
    @PostMapping("/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@RequestBody @Valid ThirdPartyDto thirdPartyDto) {
        return adminService.createThirdParty(thirdPartyDto);
    }
}