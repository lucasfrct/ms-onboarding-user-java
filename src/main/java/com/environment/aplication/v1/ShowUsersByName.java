package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

import com.environment.infrastructure.repository.UserRepository;
import com.environment.infrastructure.utils.ResponseWith;
import com.environment.domain.User;

/**
 * Lista usuários por nome
 */
@RestController
public class ShowUsersByName {
    
    @GetMapping("/api/v1/users/{name}")
    public ResponseEntity<String> index(@PathVariable String name) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            User user = gson.fromJson("{}", User.class);

            user.setFirstName( name );
            Map<String, String> valid = user.validateFirstName();
            if (valid.get("status") != "200") {
                return ResponseWith.json(valid);
            }

            UserRepository userRepository = new UserRepository();
            Map<String, String> result = userRepository.readByName(user);
            
            return ResponseWith.json(result);
        } catch (Exception e) {
            return ResponseWith.json(ResponseWith.error("ONU025"));
        }
    }
}
