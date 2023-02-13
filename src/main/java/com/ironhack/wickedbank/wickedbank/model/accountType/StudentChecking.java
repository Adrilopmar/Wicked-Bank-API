package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
    public StudentChecking() {
    }

    public StudentChecking(Money balance, String secretKey, Long ownerId, LocalDate creationDate, Status status) {
        super(balance, secretKey, ownerId, creationDate, status);
    }

    public StudentChecking(Money balance, String secretKey, Long ownerId, String secondaryOwner, BigDecimal penaltyFee, LocalDate creationDate, Status status) {
        super(balance, secretKey, ownerId, secondaryOwner, penaltyFee, creationDate, status);
    }
}
