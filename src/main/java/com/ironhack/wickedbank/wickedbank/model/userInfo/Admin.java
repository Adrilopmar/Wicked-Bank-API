package com.ironhack.wickedbank.wickedbank.model.userInfo;

import com.ironhack.wickedbank.wickedbank.model.Role;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends User {
    public Admin() {
    }

    public Admin(String name, String username, String password, List<Role> roles) {
        super(name, username, password, roles);
    }
    public Admin(String name) {
        super(name);
    }
}
