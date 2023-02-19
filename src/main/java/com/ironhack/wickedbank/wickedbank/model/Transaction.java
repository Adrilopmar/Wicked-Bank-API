package com.ironhack.wickedbank.wickedbank.model;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User senderId;
    @NotNull
    private Long senderAccountId;
    @NotNull
    private Long receiverId;
    @NotNull
    private Long receiverAccountId;
    @NotNull
    private Money amount;
    private final LocalDate transactionDate = LocalDate.now();

    public Transaction() {
    }
    public Transaction(User senderId, Long senderAccountId, Long receiverId, Long receiverAccountId, Money amount) {
        this.senderId = senderId;
        this.senderAccountId = senderAccountId;
        this.receiverId = receiverId;
        this.receiverAccountId = receiverAccountId;
//        this.receiverName = receiverName;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public User getSenderId() {
        return senderId;
    }

    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
