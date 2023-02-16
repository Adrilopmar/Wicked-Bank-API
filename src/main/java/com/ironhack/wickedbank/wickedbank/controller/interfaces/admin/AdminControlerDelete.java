package com.ironhack.wickedbank.wickedbank.controller.interfaces.admin;

import com.ironhack.wickedbank.wickedbank.controller.dto.DeleteDto;

public interface AdminControlerDelete {
   void deleteUser(Long userId, DeleteDto deleteDto);
   void deleteAccount(Long userId, DeleteDto deleteDto);
}
