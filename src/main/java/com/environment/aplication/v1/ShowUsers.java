package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Lista todos os usuários
 */
@RestController
public class ShowUsers {

    @GetMapping("/api/v1/users")
    public String index() {
        return "SHOW USERS";
    }
    
}
