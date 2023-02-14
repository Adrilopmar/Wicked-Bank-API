package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account {

        private Money creditLimit = new Money( new BigDecimal("100"));
        private BigDecimal interestRate =new BigDecimal("0.2");

    public CreditCard() {
    }
    public CreditCard(Money balance, String secretKey, Long ownerId) {
        super(balance, secretKey, ownerId);
    }
    public CreditCard(Money balance, String secretKey, Long ownerId, Money creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, ownerId);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCard(Money balance, String secretKey, Long ownerId, String secondaryOwner, BigDecimal penaltyFee, Money creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, ownerId, secondaryOwner, penaltyFee);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        if(creditLimit.getAmount().compareTo(new BigDecimal("100"))<0){
            this.creditLimit = new Money(new BigDecimal("100"));
        } else if (creditLimit.getAmount().compareTo(new BigDecimal("100000"))>0) {
            this.creditLimit = new Money( new BigDecimal("100000"));
        }else {
            this.creditLimit = creditLimit;
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(new BigDecimal("0.1"))<0){
            this.interestRate = new BigDecimal("0.1");
        } else if (interestRate.compareTo(new BigDecimal("0.2"))>0) {
            this.interestRate = new BigDecimal("0.2");
        }else {
            this.interestRate = interestRate;
        }
    }
}
