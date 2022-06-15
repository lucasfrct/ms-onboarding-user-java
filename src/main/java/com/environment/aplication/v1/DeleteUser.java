package com.environment.aplication.v1;

import com.environment.domain.User;
import com.environment.infrastructure.utils.ResponseWith;
import com.environment.infrastructure.repository.UserRepository;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

/**
 * Deleta uma usuário com base no UUID
 */
@RestController
public class DeleteUser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);
    
    @DeleteMapping("/api/v1/user/{uuid}")
    public ResponseEntity<String> index( @PathVariable String uuid ) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    
            User user = gson.fromJson("{}", User.class);
            user.setUuid( uuid );
            Map<String, String> valid = user.validateUuid();
            if (valid.get("status") != "200") {
                return ResponseWith.json(valid);
            }
            
            UserRepository userRepository = new UserRepository();
            Map<String, String> response = userRepository.delete( user );
    
            return ResponseWith.json(response);
            
        } catch (Exception e) {
            LOGGER.error("erro ao deletar usuario", e);
            return ResponseWith.json(ResponseWith.error("ONU046"));
        }
    }
}