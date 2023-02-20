package com.ironhack.wickedbank.wickedbank.controller.imp.admin.delete;

import com.ironhack.wickedbank.wickedbank.controller.dto.DeleteDto;
import com.ironhack.wickedbank.wickedbank.controller.interfaces.admin.AdminControlerDelete;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/delete")
public class AdminControllerDeleteImpl implements AdminControlerDelete {
    @Autowired
    AdminService adminService;

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@RequestParam Long userId, @RequestBody @Valid DeleteDto deleteDto,Authentication authentication){
        adminService.deleteUser(userId, deleteDto,authentication);
    }
    @DeleteMapping("/account/{accountId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAccount(@RequestParam Long accountId, @RequestBody @Valid DeleteDto deleteDto, Authentication authentication){
        adminService.deleteAccount(accountId,deleteDto,authentication);
    }

}
