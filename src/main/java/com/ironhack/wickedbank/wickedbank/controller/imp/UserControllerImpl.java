package com.ironhack.wickedbank.wickedbank.controller.imp;

import com.ironhack.wickedbank.wickedbank.controller.interfaces.UserControler;
import com.ironhack.wickedbank.wickedbank.model.User;
import com.ironhack.wickedbank.wickedbank.repository.ThirdPartyRepository;
import com.ironhack.wickedbank.wickedbank.repository.UserRepository;
import com.ironhack.wickedbank.wickedbank.service.interfeces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserControler {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.findUserById(userId);
    }
//    @GetMapping("/role/admin")
//    public List<User> getAllByRole(){
//        return userRepository.findAllByRoles("ADMIN");
//    }
}
