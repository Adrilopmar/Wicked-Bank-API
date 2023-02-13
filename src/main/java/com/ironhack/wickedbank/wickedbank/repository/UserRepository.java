package com.ironhack.wickedbank.wickedbank.repository;

import com.ironhack.wickedbank.wickedbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
