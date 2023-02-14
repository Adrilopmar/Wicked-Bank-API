package com.ironhack.wickedbank.wickedbank.model;

import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.accountType.CreditCard;
import com.ironhack.wickedbank.wickedbank.model.accountType.Savings;
import com.ironhack.wickedbank.wickedbank.model.accountType.StudentChecking;
import com.ironhack.wickedbank.wickedbank.model.userInfo.AccountHolder;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import com.ironhack.wickedbank.wickedbank.repository.AccountRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    private User accountHolder;
    private User admin;
    private User thirdParty;
    private Account studentChecking;
    private Account creditCard;
    private Account savings;
    @BeforeEach
    void setUp() {
        Address address =new Address("fake");
        LocalDate localDate = LocalDate.of(1991, 06, 26);
        LocalDate creationDate = LocalDate.of(2023, 02, 13);
        // users ===================================
        accountHolder= new AccountHolder("adri",localDate,address,"asd@asd.com");
        userRepository.save(accountHolder);
        admin = new Admin("Denna");
        userRepository.save(admin);
        thirdParty = new ThirdParty("rubik","123asd");
        userRepository.save(thirdParty);

        // accounts =============================
        studentChecking = new StudentChecking(new Money(new BigDecimal("10.50")),"123456",accountHolder.getUserId());
        accountRepository.save(studentChecking);
        savings = new Savings(new Money(new BigDecimal("100")),"123456",accountHolder.getUserId(),new BigDecimal("1"));
        accountRepository.save(savings);
        creditCard = new CreditCard(new Money(new BigDecimal("50")),"123",accountHolder.getUserId(),new Money( new BigDecimal("250")),new BigDecimal("0.285"));
        accountRepository.save(creditCard);



    }

    @AfterEach
    void tearDown() {
//        userRepository.deleteAll();
//        accountRepository.deleteAll();
    }
    @Test
    public void addUser_CorrectData_GetFromDatabase(){
        assertEquals(3,userRepository.findAll().size());
    }
    @Test
    public void addAccount_CorrectData_GetFromDatabase(){
        assertEquals(3,accountRepository.findAll().size());
    }
}