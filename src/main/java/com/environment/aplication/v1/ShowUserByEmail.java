package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Lista um usuário com base no email
 */
@RestController
public class ShowUserByEmail {
    
    @GetMapping("/api/v1/user/email/{email}")
    public String index(String email) {
        return "SHOW USER BY EMAIL";
    }
}
