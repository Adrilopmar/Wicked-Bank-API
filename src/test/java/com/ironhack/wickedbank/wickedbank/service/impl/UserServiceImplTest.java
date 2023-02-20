package com.ironhack.wickedbank.wickedbank.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.model.Role;
import com.ironhack.wickedbank.wickedbank.model.accountType.Checking;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.accountType.StudentChecking;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.AdminRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AccountHolder accountHolder;
    private Admin admin;
    private ThirdParty thirdParty;
    private StudentChecking studentChecking;
    private CreditCard creditCard;
    private Savings savings;
    private Checking checking;
    private AccountHolder accountHolderTeen;
    private AccountHolder creditCardHolder;
    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        // Admin ===================================
//        admin =new Admin();
//        admin.setName("GodFeather");
//        admin.setPassword("tomaBobo");
//        adminRepository.save(admin);



        // users ===================================
        Address address =new Address("fake");
        LocalDate localDate = LocalDate.of(1991, 06, 26);
        LocalDate localDateTest = LocalDate.of(1993, 01, 10);
        LocalDate localDateTeen = LocalDate.of(2015, 03, 15);
        LocalDate creationDate = LocalDate.of(2023, 02, 13);

        accountHolder= new AccountHolder("Sir.Duck",localDate,address,"asd@asd.com");
//        accountHolder= new AccountHolder("Sir.Duck",localDate,address,"asd@asd.com");
        accountHolderTeen= new AccountHolder("Elena",localDateTeen,address,"123@123.com");
        accountHolderTeen.setRoles(List.of(new Role("ACCOUNT_HOLDER")));
        creditCardHolder= new AccountHolder("Rafa",localDateTest,address,"hhh@hhh.com");
        userRepository.save(accountHolder);
        userRepository.save(accountHolderTeen);
        userRepository.save(creditCardHolder);

        // Accounts ================================

        creditCard = new CreditCard();
        studentChecking = new StudentChecking();
        savings = new Savings();
        accountRepository.save(creditCard);
        accountRepository.save(studentChecking);
        accountRepository.save(savings);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void findUserById_CorrectId_Result() {
//        assertEquals("Sir.Duck",userService.findUserById(accountHolder.getUserId()).getName());
    }
    @Test
    void findUserById_IncorrectId_NotFound() {
//        assertThrows(ResponseStatusException.class, ()->userService.findUserById(99999L));
    }
    @Test
    void newTransaction_CorrectData_SuccessfulTransaction() throws Exception {
        transactionDto dto = new transactionDto();
        dto.setSenderAccountId(creditCard.getAccountId());
//        dto.setReceiverAccountId(studentChecking.getAccountId());
        dto.setAmount(new Money(new BigDecimal("100")));
        String body = objectMapper.writeValueAsString(dto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/users/"+creditCardHolder.getUserId()+"/transaction/"+studentChecking.getAccountId())
//                                .param("senderAccountId", String.valueOf(creditCardHolder.getUserId()))
//                                .param("receiverId", String.valueOf(accountHolderTeen.getUserId()))
                                .param("primary-owner", creditCardHolder.getName())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rafa"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("100"));
    }
    @Test
    void newTransaction_SameAccountData_Declined() throws Exception {
        transactionDto dto = new transactionDto();
        dto.setSenderAccountId(7L);
//        dto.setReceiverAccountId(7L);
        dto.setAmount(new Money(new BigDecimal("100")));

        String body = objectMapper.writeValueAsString(dto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/users/7/transaction/7")
//                                .param("senderId","7")
//                                .param("receiverId","7")
                                .param("primary-owner", "titi")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
        assertThrows(ResponseStatusException.class,()->userService.newTransaction(7L,7L,"titi",null,dto, null));

    }
    @Test
    void thirdPartyTransaction() throws Exception {
        ///third-party/{senderId}/transaction/{receiverId}
        transactionDto dto = new transactionDto();
        dto.setSenderAccountId(checking.getAccountId());
        dto.setAmount(new Money(new BigDecimal("200")));
        String body = objectMapper.writeValueAsString(dto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/users/third-party/"+thirdParty.getUserId()+"/transaction/"+studentChecking.getAccountId())
                                .header("hashed-key","123")
                                .param("secret-key", "123")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted())
                .andReturn();
    }
}