package com.ironhack.wickedbank.wickedbank.model.userInfo;

import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.enums.Role;
import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AccountHolder extends User {
    private LocalDate dateOfBirth;
    @Embedded
    private Address address;
    private String mailingAddress;
    private final Role role = Role.ACCOUNT_HOLDER;


    public AccountHolder() {}

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
