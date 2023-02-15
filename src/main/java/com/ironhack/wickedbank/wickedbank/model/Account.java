package com.ironhack.wickedbank.wickedbank.model;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import com.ironhack.wickedbank.wickedbank.enums.Status;
import com.ironhack.wickedbank.wickedbank.enums.Type;
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
            @AttributeOverride(name = "amount", column = @Column(name = "amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money balance;
    private String secretKey;
//    @ManyToOne
//    @JoinColumn(name = "owner")
//    private User user;
    private Long ownerId;
    private Long secondaryOwner;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_amount")),
            @AttributeOverride(name = "currency", column = @Column(insertable = false,updatable=false))
    })
    private Money penaltyFee = new Money(new BigDecimal("40"));

    private LocalDate creationDate = LocalDate.now();
    private Status status = Status.ACTIVE;
    @ManyToMany
    @JoinTable(
            name = "account_link_user",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> owners;
    private Type type;

    public Account() {
    }
    public Account(
            String secretKey,
            Long ownerId) {
        this.secretKey = secretKey;
        this.ownerId = ownerId;
    }
    public Account(
                   Money balance,
                   String secretKey,
                   Long ownerId) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.ownerId = ownerId;
    }

    public Account(Money balance,
                   String secretKey,
                   Long ownerId,
                   Long secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.ownerId = ownerId;
        this.secondaryOwner = secondaryOwner;
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

    public Long getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Long secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<User> getOwners() {
        return owners;
    }

    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", secretKey='" + secretKey + '\'' +
                ", ownerId=" + ownerId +
                ", secondaryOwner=" + secondaryOwner +
                ", penaltyFee=" + penaltyFee +
                ", creationDate=" + creationDate +
                ", status=" + status +
                ", owners=" + owners +
                ", type=" + type +
                '}';
    }
}
