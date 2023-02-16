package com.ironhack.wickedbank.wickedbank.model.userInfo;

import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends User {
    public Admin() {
    }

    public Admin(String name) {
        super(name);
    }
}
