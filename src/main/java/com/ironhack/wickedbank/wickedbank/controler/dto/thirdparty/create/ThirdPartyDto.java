package com.ironhack.wickedbank.wickedbank.controler.dto.thirdparty.create;

import com.ironhack.wickedbank.wickedbank.classes.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ThirdPartyDto extends User {
    @NotNull
    private String hashedKey;

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
