package com.ironhack.wickedbank.wickedbank.classes;

import jakarta.persistence.Embeddable;

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
