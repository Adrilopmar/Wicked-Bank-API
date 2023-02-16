package com.ironhack.wickedbank.wickedbank.controller.dto.accountholder.update;

import com.ironhack.wickedbank.wickedbank.classes.Address;
import com.ironhack.wickedbank.wickedbank.classes.User;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AccountHolderPutDto extends User {
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @Embedded
    private Address address;
    @NotNull
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
