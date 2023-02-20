package com.ironhack.wickedbank.wickedbank.controller.dto.savings.create;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SavingsDto {

    private Money balance;
    @NotNull
    private String secretKey;
    @NotNull
    private Long ownerId;
    private Long secondaryOwner;
    private Money penaltyFee;
    @Digits(integer = 1, fraction = 4)
    private BigDecimal interestRate;

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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    @Override
    public String toString() {
        return
                "balance=" + balance +
                ", secretKey='" + secretKey + '\'' +
                ", ownerId=" + ownerId +
                ", secondaryOwner='" + secondaryOwner + '\'' +
                ", interestRate=" + interestRate;
    }
}
