package com.environment.aplication.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.Valid;

import com.environment.infrastructure.utils.ResponseWith;
import com.environment.domain.User;

/**
 * Cria um novo usuario
 */
@RestController
public class CreateUser {    
    @PostMapping("/api/v1/user")
    public ResponseEntity<String> index(@Valid @RequestBody String body, HttpServletRequest servletRequest) {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        
        // preenche a classe do usuario
        User user = gson.fromJson(body, User.class);
        
        // Gera um UUID aleatório
        user.setUuid(UUID.randomUUID().toString());
    
        // recebe e trata as violacoes
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> violation : violations) {
            
            // cria uma estrutura do tipo mapa para cada violacao e gera uma resposta padrao
            Type errorType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> erro = gson.fromJson(violation.getMessage(), errorType);
            ResponseWith responseWith = new ResponseWith(erro);
            return responseWith.json();
            
        }
        
        // valida o usuario
        ResponseWith responseWith = new ResponseWith(user.validate());
        // Map<String, String> response = new HashMap<String, String>();
        // ResponseWith responseWith = new ResponseWith(response);

        // criptografa a senha
        user.passwordHash();

        // monta o fullName
        user.fullName = user.firstName+" "+user.lastName;
        
        return responseWith.json();
    }
    
}
