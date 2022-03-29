package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Lista um usuário com base no UUID
 */
@RestController
public class ShowUser {
    
    @GetMapping("/api/v1/user/{uuid}")
    public String index(String uuid) {
        return "SHOW USER";
    }
}
