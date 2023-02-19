package com.ironhack.wickedbank.wickedbank.repository;

import com.ironhack.wickedbank.wickedbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);
    List<User> findAllByRoles(String roles);
    Optional<User> findByUsername(String username);
}
