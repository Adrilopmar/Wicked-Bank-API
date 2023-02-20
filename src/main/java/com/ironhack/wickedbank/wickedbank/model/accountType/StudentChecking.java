package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.model.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {

    public StudentChecking() {
    }

    public StudentChecking(Money balance, String secretKey, Long ownerId) {
        super(balance, secretKey, ownerId);
    }

    public StudentChecking(Money balance, String secretKey, Long ownerId, Long secondaryOwner) {
        super(balance, secretKey, ownerId, secondaryOwner);
    }
}
