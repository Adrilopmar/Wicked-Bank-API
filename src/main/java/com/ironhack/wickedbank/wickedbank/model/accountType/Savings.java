package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.Account;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account {

    private BigDecimal interestRate =new BigDecimal("0.0025");

    public Savings() {
    }
    public Savings(String secretKey, Long ownerId) {
        super(secretKey, ownerId);
        setBalance(new Money( new BigDecimal("1000")));
    }
    public Savings(Money balance, String secretKey, Long ownerId) {
        super(balance, secretKey, ownerId);
        if(balance.getAmount().compareTo(new BigDecimal("100"))<0){
            setBalance(new Money( new BigDecimal("100")));
        }
    }
    public Savings(Money balance, String secretKey, Long ownerId, BigDecimal interestRate) {
        super(balance, secretKey, ownerId);
        if(balance.getAmount().compareTo(new BigDecimal("100"))<0){
            setBalance(new Money( new BigDecimal("100")));
        }
        setInterestRate(interestRate);
    }

    public Savings(Money balance, String secretKey, Long ownerId, String secondaryOwner, BigDecimal penaltyFee, BigDecimal interestRate) {
        super(balance, secretKey, ownerId, secondaryOwner, penaltyFee);
        setInterestRate(interestRate);
    }
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interest) {
        if (interest.compareTo(new BigDecimal("0.0025"))<=0){
            this.interestRate = new BigDecimal( "0.0026");
        } else if (interest.compareTo(new BigDecimal("0.5"))>1) {
            this.interestRate =new BigDecimal("0.5");
        }else {
            this.interestRate = interest;
        }
    }
}
