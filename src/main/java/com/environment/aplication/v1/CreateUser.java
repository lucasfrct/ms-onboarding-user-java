package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;


import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.Valid;

import com.environment.domain.User;

/**
 * Cria um novo usuario
 */
@RestController
public class CreateUser {    
    @PostMapping("/api/v1/user")
    public ResponseEntity<String> index(@Valid @RequestBody String body) {

        
        Logger log = Logger.getGlobal();
        
        log.setLevel(Level.FINE);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        // Preenche a classe user com body
        User user = gson.fromJson(body, User.class);

        // Gera um UUID aleatório
        user.setUuid(UUID.randomUUID().toString());
        
        

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> violation : violations) {
            // log.error(violation.getMessage()); 
            // log.warning(violation.getMessage());
            System.out.println("validation: "+violation.getMessage());
        }

        Map<String, String> validate = user.validate();
        if (!Boolean.parseBoolean(validate.get("status"))) {
            String error = gson.toJson(validate);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        user.passwordHash();
        user.fullName = user.firstName+" "+user.lastName;
        return ResponseEntity.status(HttpStatus.CREATED).body("");

    }
    
}
