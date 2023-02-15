package com.ironhack.wickedbank.wickedbank.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.enums.Type;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.accountType.StudentChecking;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class AdminServiceImplTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private AccountHolder accountHolder;
    private AccountHolder accountHolderTeen;
    private Admin admin;
    private ThirdParty thirdParty;
    private StudentChecking studentChecking;
    private CreditCard creditCard;
    private Savings savings;

    @BeforeEach
    void setUp() {
        Address address =new Address("fake");
        LocalDate localDate = LocalDate.of(1991, 06, 26);
        LocalDate localDateTeen = LocalDate.of(2015, 03, 15);
        LocalDate creationDate = LocalDate.of(2023, 02, 13);
        // users ===================================
        accountHolder= new AccountHolder("Sir.Duck",localDate,address,"asd@asd.com");
        accountHolderTeen= new AccountHolder("Elena",localDateTeen,address,"123@123.com");
        userRepository.save(accountHolder);
        userRepository.save(accountHolderTeen);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {

        accountRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void createSaving_AdminFromApi_Result() throws Exception {
        SavingsDto savingsDto = new SavingsDto();
        savingsDto.setSecretKey("yaya");
        savingsDto.setOwnerId(1L);
        String body = objectMapper.writeValueAsString(savingsDto);
        MvcResult mvcResult = mockMvc.perform(
                post("/admin/new/saving")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("yaya"));
    }

    @Test
    void createChecking_AdultAccount_CheckingCreated() throws Exception {
        CheckingDto checkingDto = new CheckingDto();
        checkingDto.setOwnerId(accountHolder.getUserId());
        checkingDto.setSecretKey("123123");
        String body = objectMapper.writeValueAsString(checkingDto);
        MvcResult mvcResult = mockMvc.perform(
                post("/admin/new/checking")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(Type.CHECKING,userRepository.findById(accountHolder.getUserId()).get().getAccounts().get(0).getType());
    }
    @Test
    void createChecking_TeenagerAccount_CheckingCreated() throws Exception {
        CheckingDto checkingDto = new CheckingDto();
        checkingDto.setOwnerId(accountHolderTeen.getUserId());
        checkingDto.setSecretKey("123123");
        String body = objectMapper.writeValueAsString(checkingDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/checking")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(Type.STUDENT_CHECKING,userRepository.findById(accountHolderTeen.getUserId()).get().getAccounts().get(0).getType());
    }
}