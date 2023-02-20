package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account {
    @Digits(integer = 1, fraction = 4)
    private BigDecimal interestRate =new BigDecimal("0.0025");

    private Money minimumBalance = new Money(new BigDecimal("1000"));
    private LocalDate lastInterestUpdate = super.getCreationDate();

    public Savings() {
    }
    public Savings(String secretKey, Long ownerId) {
        super(secretKey, ownerId);
        setBalance(new Money( new BigDecimal("1000")));
    }
    public Savings(Money balance, String secretKey, Long ownerId, BigDecimal interestRate) {
        super(balance, secretKey, ownerId);
        if(balance.getAmount().compareTo(new BigDecimal("100"))<0){
            setBalance(new Money( new BigDecimal("100")));
        }
        setInterestRate(interestRate);
    }

    public Savings(Money balance, String secretKey, Long ownerId, Long secondaryOwner, BigDecimal interestRate) {
        super(balance, secretKey, ownerId, secondaryOwner);
        setInterestRate(interestRate);
    }

    public void updateInterest() {
        LocalDate currentDate = LocalDate.now();
        while ((lastInterestUpdate.isBefore(currentDate.minusMonths(1)))) {
            BigDecimal interest = getBalance().getAmount().multiply(getInterestRate());
            getBalance().increaseAmount(interest);
            lastInterestUpdate = lastInterestUpdate.plusMonths(1);
        }
    }
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interest) {
        if (interest.compareTo(new BigDecimal("0.0025"))<=0){
            this.interestRate = new BigDecimal( "0.0026");
        } else if (interest.compareTo(new BigDecimal("0.5")) > 0) {
            this.interestRate =new BigDecimal("0.5");
        }else {
            this.interestRate = interest;
        }
    }

    public LocalDate getLastInterestUpdate() {
        return lastInterestUpdate;
    }

    public void setLastInterestUpdate(LocalDate lastInterestUpdate) {
        this.lastInterestUpdate = lastInterestUpdate;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
