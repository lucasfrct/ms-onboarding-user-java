package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * atualiza um usuário
 */
@RestController
public class UpdateUser {
    
    @PutMapping("/api/v1/user/{uuid}")
    public String index(String uuid) {
        return "UPDATE USER";
    }
}
