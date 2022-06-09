package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import com.environment.infrastructure.utils.ResponseWith;
import com.environment.infrastructure.repository.UserRepository;

/**
 * Lista todos os usuários
 */
@RestController
public class ShowUsers {

    @GetMapping("/api/v1/users")
    public ResponseEntity<String> index() {

        UserRepository userRepository = new UserRepository();
        Map<String, String> response = userRepository.readUsers();

        return ResponseWith.json(response);
    }
    
}
