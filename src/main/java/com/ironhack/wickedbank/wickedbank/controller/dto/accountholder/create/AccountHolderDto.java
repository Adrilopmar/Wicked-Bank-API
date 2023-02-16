package com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.create;

import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.classes.User;
import jakarta.persistence.Embedded;

import java.time.LocalDate;

public class AccountHolderDto extends User {
    private LocalDate dateOfBirth;
    @Embedded
    private Address address;
    private String mailingAddress;

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
