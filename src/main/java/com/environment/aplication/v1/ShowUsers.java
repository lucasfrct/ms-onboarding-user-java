package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.environment.infrastructure.utils.ResponseWith;
import com.environment.infrastructure.repository.UserRepository;

/**
 * Lista todos os usuários
 */
@RestController
public class ShowUsers {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);

    @GetMapping("/api/v1/users")
    public ResponseEntity<String> index() {
        try {
            UserRepository userRepository = new UserRepository();
            Map<String, String> response = userRepository.readUsers();
    
            return ResponseWith.json(response);
            
        } catch (Exception e) {
            LOGGER.error("erro ao buscar usuarios", e);
            return ResponseWith.json(ResponseWith.error("ONU047"));
        }
    }
    
}
