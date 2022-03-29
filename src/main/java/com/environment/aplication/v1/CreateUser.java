package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Cria um novo usuario
 */
@RestController
public class CreateUser {
    
    @PostMapping("/api/v1/user")
    public String index() {
        return "CREATE USER";
    }
    
}
