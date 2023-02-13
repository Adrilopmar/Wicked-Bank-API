package com.ironhack.wickedbank.wickedbank.model;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    private Money balance;
    private String secretKey;
//    @ManyToOne
//    @JoinColumn(name = "owner")
//    private User user;
    private Long ownerId;
    private String secondaryOwner;
    private BigDecimal penaltyFee;
    private LocalDate creationDate;
    private Status status;
    @ManyToMany
    @JoinTable(
            name = "account_link_user",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> owners;

    public Account() {
    }
    public Account(
            String secretKey,
            Long ownerId,
            LocalDate creationDate,
            Status status) {
        this.secretKey = secretKey;
        this.ownerId = ownerId;
        this.penaltyFee = new BigDecimal("40");
        this.creationDate = creationDate;
        this.status = status;
    }
    public Account(
                   Money balance,
                   String secretKey,
                   Long ownerId,
                   LocalDate creationDate,
                   Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.ownerId = ownerId;
        this.penaltyFee = new BigDecimal("40");
        this.creationDate = creationDate;
        this.status = status;
    }

    public Account(Money balance,
                   String secretKey,
                   Long ownerId,
                   String secondaryOwner,
                   BigDecimal penaltyFee,
                   LocalDate creationDate,
                   Status status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.ownerId = ownerId;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = penaltyFee;
        this.creationDate = creationDate;
        this.status = status;
    }
    public void isLessThanMinimum(){}

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

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

    public Long getOwner() {
        return ownerId;
    }

    public void setOwner(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
