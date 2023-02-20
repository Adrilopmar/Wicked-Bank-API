package com.ironhack.wickedbank.wickedbank.repository;

import com.ironhack.wickedbank.wickedbank.model.userInfo.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty,Long> {

}
