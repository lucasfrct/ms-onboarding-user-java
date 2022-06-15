package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.environment.infrastructure.repository.UserRepository;
import com.environment.infrastructure.utils.ResponseWith;
import com.environment.domain.User;

/**
 * Lista um usuário com base no email
 */
@RestController
public class ShowUserByEmail {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);
    
    @GetMapping("/api/v1/user/email/{email}")
    public ResponseEntity<String> index(@PathVariable @Valid String email) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            User user = gson.fromJson("{}", User.class);

            user.setEmail( email );
            Map<String, String> valid = user.validateEmail();
            if (valid.get("status") != "200") {
                return ResponseWith.json(valid);
            }

            UserRepository userRepository = new UserRepository();
            Map<String, String> result = userRepository.readByEmail(user);
            
            return ResponseWith.json(result);
        } catch (Exception e) {
            LOGGER.error("erro ao buscar usuario", e);
            return ResponseWith.json(ResponseWith.error("ONU048"));
        }
    }
}
