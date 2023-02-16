package com.ironhack.wickedbank.wickedbank.classes;


import jakarta.validation.constraints.NotNull;

public abstract class User {

    @NotNull
    private String name;
    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
