package com.ironhack.wickedbank.wickedbank.classes;

import com.ironhack.wickedbank.wickedbank.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public abstract class Account {
    @NotNull
    private Money balance;
    @NotNull
    private String secretKey;
    @NotNull
    private Long ownerId;
    private Long secondaryOwner;
    private Money penaltyFee;
    private LocalDate creationDate;
    private Status status;

}
