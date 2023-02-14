package com.ironhack.wickedbank.wickedbank.model.userInfo;

import com.ironhack.wickedbank.wickedbank.enums.Role;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class ThirdParty extends User {
    private String hashedKey;
    private final Role role = Role.THIRD_PARTY;

    public ThirdParty() {
    }
    public ThirdParty(String hashedKey) {
        this.hashedKey = hashedKey;
    }
    public ThirdParty(String name, String hashedKey) {
        super(name);
        this.hashedKey = hashedKey;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
