package com.ironhack.wickedbank.wickedbank.model.accountType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.model.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account {

    private Money minimumBalance = new Money(new BigDecimal("250"));
@Embedded
@AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance")),
        @AttributeOverride(name = "currency", column = @Column(insertable = false,updatable=false))
})
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"));
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate birthDate;

    public Checking() {
    }

    public Checking(String secretKey, Long ownerId) {
        super(secretKey, ownerId);
    }

    public Checking(Money balance, String secretKey, Long ownerId, Money monthlyMaintenanceFee) {
        super(balance, secretKey, ownerId);
        setMinimumBalance(minimumBalance);
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }
//
    public Checking(Money balance, String secretKey, Long ownerId, Long secondaryOwner, Money monthlyMaintenanceFee) {
        super(balance, secretKey, ownerId, secondaryOwner);
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
