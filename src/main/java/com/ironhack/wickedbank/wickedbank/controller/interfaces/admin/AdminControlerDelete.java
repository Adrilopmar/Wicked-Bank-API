package com.ironhack.wickedbank.wickedbank.controller.interfaces.admin;

import com.ironhack.wickedbank.wickedbank.controller.dto.DeleteDto;
import org.springframework.security.core.Authentication;

public interface AdminControlerDelete {
   void deleteUser(Long userId, DeleteDto deleteDto, Authentication authentication);
   void deleteAccount(Long userId, DeleteDto deleteDto,Authentication authentication);
}
