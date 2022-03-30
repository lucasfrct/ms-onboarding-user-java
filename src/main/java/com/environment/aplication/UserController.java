package com.environment.aplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.environment.domain.User;
import com.environment.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @RequestMapping("/user")
    public String index() {

        User user = new UserService();
        System.out.println(user);
        return "uesr a";
    }
    
}
