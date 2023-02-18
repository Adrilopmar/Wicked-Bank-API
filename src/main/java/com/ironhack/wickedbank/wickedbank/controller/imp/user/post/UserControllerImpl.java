package com.ironhack.wickedbank.wickedbank.controller.imp.user.post;

import com.ironhack.wickedbank.wickedbank.controller.dto.transaction.transactionDto;
import com.ironhack.wickedbank.wickedbank.controller.interfaces.user.UserControler;
import com.ironhack.wickedbank.wickedbank.model.Transaction;
import com.ironhack.wickedbank.wickedbank.repository.ThirdPartyRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserControler {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @PostMapping("/{senderId}/transaction/{receiverId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction newTransaction(@RequestParam (name = "senderId") Long senderId, @RequestParam (name = "receiverId") Long receiverId,
                                      @RequestBody @Valid transactionDto transactionDto){
        return userService.newTransaction(senderId,receiverId,transactionDto);
    }

//    @GetMapping("/role/admin")
//    public List<User> getAllByRole(){
//        return userRepository.findAllByRoles("ADMIN");
//    }
}
