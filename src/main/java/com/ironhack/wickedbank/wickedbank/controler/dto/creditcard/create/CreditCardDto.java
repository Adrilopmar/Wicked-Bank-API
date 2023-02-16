package com.ironhack.wickedbank.wickedbank.controler.dto.creditcard.create;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import jakarta.validation.constraints.NotNull;

public class CreditCardDto {
    private Money balance;
    @NotNull
    private String secretKey;
    @NotNull
    private Long ownerId;
    private Long secondaryOwner;

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Long secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }
}
