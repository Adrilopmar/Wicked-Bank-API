package com.ironhack.wickedbank.wickedbank.repository;

import com.ironhack.wickedbank.wickedbank.model.Role;
import com.ironhack.wickedbank.wickedbank.model.userInfo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    List<Admin> findAllByRoles(Role role);
    Admin findByName(String name);
}
