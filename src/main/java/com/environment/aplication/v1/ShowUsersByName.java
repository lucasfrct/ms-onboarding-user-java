package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Lista  usuários pro nome
 */
@RestController
public class ShowUsersByName {
    
    @GetMapping("/api/v1/users/{name}")
    public String index(String name) {
        return "SHOW USERS BY NAME";
    }
}
