package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account {

    private Money minimumBalance = new Money(new BigDecimal("250"));

    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"));

    public Checking() {
    }

    public Checking(String secretKey, Long ownerId, LocalDate creationDate, Status status) {
        super(secretKey, ownerId, creationDate, status);
    }

    public Checking(Money balance, String secretKey, Long ownerId, LocalDate creationDate, Status status, Money minimumBalance, Money monthlyMaintenanceFee) {
        super(balance, secretKey, ownerId, creationDate, status);
        setMinimumBalance(minimumBalance);
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }
//
    public Checking(Money balance, String secretKey, Long ownerId, String secondaryOwner, BigDecimal penaltyFee, LocalDate creationDate, Status status, Money minimumBalance, Money monthlyMaintenanceFee) {
        super(balance, secretKey, ownerId, secondaryOwner, penaltyFee, creationDate, status);
        setMinimumBalance(minimumBalance);
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        if(minimumBalance.getAmount().compareTo(new BigDecimal("250"))<0){
            this.minimumBalance = new Money(new BigDecimal("250"));
        }else {
            this.minimumBalance = minimumBalance;
        }
    }

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }
}
