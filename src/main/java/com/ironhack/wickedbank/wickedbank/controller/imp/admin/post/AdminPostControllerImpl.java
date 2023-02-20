package com.ironhack.wickedbank.wickedbank.controller.imp.admin.post;

import com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.creditcard.create.CreditCardDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controller.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.controller.interfaces.admin.AdminControler;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
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

    // ========================= Accounts =============================
    @PostMapping("/saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createSaving(@RequestBody @Valid SavingsDto savingsDto, Authentication authentication){//, Authentication authentication
       return adminService.createSaving(savingsDto,authentication);//, authentication.getName()
    }
    @PostMapping("/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createChecking(@RequestBody @Valid CheckingDto checkingDto,Authentication authentication){
        return  adminService.createChecking(checkingDto,authentication);
    }

    @PostMapping("/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCard(@RequestBody @Valid CreditCardDto creditCardDto,Authentication authentication) {
        return adminService.createCreditCard(creditCardDto,authentication);
    }

    // ========================= Users =============================
    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin AddAdmin(@RequestBody @Valid AdminDto adminDto,Authentication authentication) {
        return adminService.createAdmin(adminDto,authentication);
    }
    @PostMapping("/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolderDto accountHolderDto,Authentication authentication) {
        return adminService.createAccountHolder(accountHolderDto,authentication);
    }
    @PostMapping("/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@RequestBody @Valid ThirdPartyDto thirdPartyDto,Authentication authentication) {
        return adminService.createThirdParty(thirdPartyDto,authentication);
    }
}
