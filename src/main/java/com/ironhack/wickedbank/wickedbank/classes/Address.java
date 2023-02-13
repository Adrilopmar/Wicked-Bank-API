package com.ironhack.wickedbank.wickedbank.classes;

import com.ironhack.wickedbank.wickedbank.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Embeddable
public class Address {
    private String street;

    public Address() {
    }

    public Address(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
