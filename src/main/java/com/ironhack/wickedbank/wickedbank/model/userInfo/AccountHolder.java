package com.ironhack.wickedbank.wickedbank.model.userInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.model.Role;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AccountHolder extends User {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dateOfBirth;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Embedded
    private Address address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mailingAddress;


    public AccountHolder() {}

    public AccountHolder(String name, String username, String password, List<Role> roles) {
        super(name, username, password, roles);
    }

    public AccountHolder(String name, LocalDate dateOfBirth, Address address) {
        super(name);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
    public AccountHolder(String name, LocalDate dateOfBirth, Address address, String mailingAddress ) {
        super(name);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
