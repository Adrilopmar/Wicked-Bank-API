package com.ironhack.wickedbank.wickedbank.controler.interfaces;

import java.time.LocalDate;

public interface AdminControler {
    void createSaving(String secretKey, Long ownerId, LocalDate creationDate);
}
