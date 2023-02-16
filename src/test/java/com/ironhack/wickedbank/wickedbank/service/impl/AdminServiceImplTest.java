package com.ironhack.wickedbank.wickedbank.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controler.dto.accountholder.create.AccountHolderDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.admin.AdminDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.checking.create.CheckingDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.savings.create.SavingsDto;
import com.ironhack.wickedbank.wickedbank.controler.dto.thirdparty.create.ThirdPartyDto;
import com.ironhack.wickedbank.wickedbank.enums.Type;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.accountType.StudentChecking;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.AdminRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class AdminServiceImplTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AdminServiceImpl adminService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private AccountHolder accountHolder;
    private AccountHolder accountHolderTeen;
    private AccountHolder creditCardHolder;
    private Admin admin;
    private ThirdParty thirdParty;
    private StudentChecking studentChecking;
    private CreditCard creditCard;
    private Savings savings;

    @BeforeEach
    void setUp() {
        Address address =new Address("fake");
        LocalDate localDate = LocalDate.of(1991, 06, 26);
        LocalDate localDateTest = LocalDate.of(1993, 01, 10);
        LocalDate localDateTeen = LocalDate.of(2015, 03, 15);
        LocalDate creationDate = LocalDate.of(2023, 02, 13);
        // users ===================================
//        Optional<Account> opAcc = accountRepository.findById(1L);
//        if(!opAcc.isPresent()){

        accountHolder= new AccountHolder("Sir.Duck",localDate,address,"asd@asd.com");
//        accountHolder= new AccountHolder("Sir.Duck",localDate,address,"asd@asd.com");
        accountHolderTeen= new AccountHolder("Elena",localDateTeen,address,"123@123.com");
        creditCardHolder= new AccountHolder("Rafa",localDateTest,address,"hhh@hhh.com");
        userRepository.save(accountHolder);
        userRepository.save(accountHolderTeen);
        userRepository.save(creditCardHolder);
//        }

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
        savingsDto.setOwnerId(accountHolder.getUserId());
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
    @Test
    void createCreditCard_DefaultData_Result() throws Exception {
        CreditCard creditDto = new CreditCard();
        creditDto.setOwnerId(creditCardHolder.getUserId());
        creditDto.setSecretKey("123123");
        String body = objectMapper.writeValueAsString(creditDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/credit-card")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rafa"));
    }
    @Test
    void createCreditCard_InputAllDataCorrect_Result() throws Exception {
        CreditCard creditDto = new CreditCard();
        creditDto.setOwnerId(creditCardHolder.getUserId());
        creditDto.setSecretKey("123123");
        creditDto.setBalance(new Money(new BigDecimal("300")));
        creditDto.setSecondaryOwner(accountHolder.getUserId());
        String body = objectMapper.writeValueAsString(creditDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/credit-card")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("300"));
    }
    @Test
    void createCreditCard_UserNotRegisteredInDatabase_UserNotFound() throws Exception {
        CreditCard creditDto = new CreditCard();
        creditDto.setOwnerId(9999L);
        creditDto.setSecretKey("123123");
        String body = objectMapper.writeValueAsString(creditDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/creditcard")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals(404,mvcResult.getResponse().getStatus());
    }
    @Test
    void checkUserInDatabase_CorrectData_Result() {
        assertTrue(adminService.checkUserInDatabase(accountHolder.getUserId()));
    }
//    @Test
//    void checkUserInDatabase_IncorrectData_Result() {
//        assertEquals(HttpStatus.NOT_FOUND,adminService.checkUserInDatabase(9999L));
//    }

    @Test
    void createAdmin_FirstAdminCreatedCorrectly_Result() throws Exception {
        adminRepository.deleteAll();
        AdminDto adminDto = new AdminDto();
        adminDto.setName("adri");
        adminDto.setPassword("123123");
        String body = objectMapper.writeValueAsString(adminDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/admin")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("123"));
    }
    @Test
    void createAdmin_SecondAdminCreated_NotAllowed() throws Exception {
        Admin firstAdmin =new Admin();
        userRepository.save(firstAdmin);
        AdminDto adminDto = new AdminDto();
        adminDto.setName("pablo");
        adminDto.setPassword("123123");
        String body = objectMapper.writeValueAsString(adminDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/admin")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden())
                .andReturn();
        assertEquals(403,mvcResult.getResponse().getStatus());
    }

    @Test
    void createAccountHolder_CorrectDefaultData_Result() throws Exception {
        AccountHolderDto accHolderDto = new AccountHolderDto();
        accHolderDto.setName("chuso");
        accHolderDto.setPassword("123123");
        String body = objectMapper.writeValueAsString(accHolderDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/account-holder")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("chuso"));
    }
    @Test
    void createAccountHolder_CorrectInputAllData_Result() throws Exception {
        AccountHolderDto accHolderDto = new AccountHolderDto();
        accHolderDto.setName("chuso");
        accHolderDto.setPassword("123123");
        accHolderDto.setMailingAddress("chuso@chuso");
        accHolderDto.setDateOfBirth(LocalDate.of(1996,05,30));
        accHolderDto.setAddress(new Address("feka avenue"));
        String body = objectMapper.writeValueAsString(accHolderDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/account-holder")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("chuso"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("feka"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("caquita veloz"));
    }

    @Test
    void createThirdParty_CorrectData_Result() throws Exception {
        ThirdPartyDto thirdPartyDto = new ThirdPartyDto();
        thirdPartyDto.setName("chuso");
        thirdPartyDto.setPassword("123123");
        thirdPartyDto.setHashedKey("makena");
        String body = objectMapper.writeValueAsString(thirdPartyDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/third-party")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("makena"));
    }
    @Test
    void createThirdParty_IncorrectData_Error() throws Exception {
        ThirdPartyDto thirdPartyDto = new ThirdPartyDto();
        String body = objectMapper.writeValueAsString(thirdPartyDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/admin/new/third-party")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}