package com.ironhack.wickedbank.wickedbank.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.AdminRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    private AccountHolder accountHolder;
    @BeforeEach
    void setUp() {
        Address address =new Address("fake");
        LocalDate localDate = LocalDate.of(1991, 06, 26);
        accountHolder= new AccountHolder("Sir.Duck",localDate,address,"asd@asd.com");
        userRepository.save(accountHolder);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findUserById_CorrectId_Result() {
        assertEquals("Sir.Duck",userService.findUserById(accountHolder.getUserId()).getName());
    }
    @Test
    void findUserById_IncorrectId_NotFound() {
        assertEquals(ResponseStatusException.class ,userService.findUserById(99999L));
    }
}