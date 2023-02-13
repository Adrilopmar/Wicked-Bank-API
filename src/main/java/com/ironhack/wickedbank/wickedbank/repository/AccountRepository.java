package com.ironhack.wickedbank.wickedbank.repository;

import com.ironhack.wickedbank.wickedbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
}
