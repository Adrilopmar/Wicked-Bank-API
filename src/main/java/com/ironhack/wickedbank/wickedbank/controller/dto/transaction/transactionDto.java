package com.ironhack.wickedbank.wickedbank.controller.dto.transaction;

import com.ironhack.wickedbank.wickedbank.classes.Money;
import jakarta.validation.constraints.NotNull;

public class transactionDto {

    @NotNull
    private Long senderAccountId;

//    private Long receiverAccountId;
    @NotNull
    private Money amount;


    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }


//    public Long getReceiverAccountId() {
//        return receiverAccountId;
//    }
//
//    public void setReceiverAccountId(Long receiverAccountId) {
//        this.receiverAccountId = receiverAccountId;
//    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
