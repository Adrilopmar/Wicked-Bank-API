package com.ironhack.wickedbank.wickedbank.classes;


import jakarta.validation.constraints.NotNull;

public abstract class User {

    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String username;

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

    public String getUsername() {
        return username;
    }
    public void setUserName(String username) {
        this.username = username;
    }
}
