package com.ironhack.wickedbank.wickedbank.controller.dto.transaction;

import jakarta.validation.constraints.NotNull;

public class TransactionThirdPartyDto extends transactionDto{
    @NotNull
    private String hashedKey;

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
