package com.ironhack.wickedbank.wickedbank.controler.interfaces.admin;

import com.ironhack.wickedbank.wickedbank.controler.dto.DeleteDto;

public interface AdminControlerDelete {
   void deleteUser(Long userId, DeleteDto deleteDto);
   void deleteAccount(Long userId, DeleteDto deleteDto);
}
