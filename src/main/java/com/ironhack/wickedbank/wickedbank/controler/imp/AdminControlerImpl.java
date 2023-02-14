package com.ironhack.wickedbank.wickedbank.controler.imp;

import com.ironhack.wickedbank.wickedbank.controler.interfaces.AdminControler;
import com.ironhack.wickedbank.wickedbank.service.interfeces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AdminControlerImpl implements AdminControler {
    @Autowired
    AdminService adminService;

    @PostMapping("/admin/new/saving")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSaving(@RequestBody String secretKey, Long ownerId, LocalDate creationDate){}
}
