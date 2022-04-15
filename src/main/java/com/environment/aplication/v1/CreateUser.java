package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.Valid;

import com.environment.domain.User;
import com.environment.infrastructure.utils.ResponseMiddleware;
import com.environment.infrastructure.utils.ResponseWith;

/**
 * Cria um novo usuario
 */
@RestController
@ResponseMiddleware
public class CreateUser {    
    @PostMapping("/api/v1/user")
    public ResponseEntity<String> index(@Valid @RequestBody String body, HttpServletRequest servletRequest) {
        
        
        Logger log = Logger.getGlobal();
        
        log.setLevel(Level.FINE);
        
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        
        User user = gson.fromJson(body, User.class);
        
        
        // Gera um UUID aleatório
        user.setUuid(UUID.randomUUID().toString());
        
        
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> violation : violations) {
            
            Type errorType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> erro = gson.fromJson(violation.getMessage(), errorType);
            ResponseWith responseWith = new ResponseWith(erro);
            return responseWith.json();
            
        }
        
        ResponseWith responseWith = new ResponseWith(user.validate());
        user.passwordHash();
        user.fullName = user.firstName+" "+user.lastName;
        
        return responseWith.json();
    }
    
}
