package com.ironhack.wickedbank.wickedbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    @ManyToMany(mappedBy="owners",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Account> accounts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private List<Role> roles;
    @OneToMany(mappedBy = "senderId",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;

    public User() {
    }

    public User(String name, String username, String password, List<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
