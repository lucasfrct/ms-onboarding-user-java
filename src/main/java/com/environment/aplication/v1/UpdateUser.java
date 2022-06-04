package com.environment.aplication.v1;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.Valid;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import com.environment.infrastructure.repository.UserRepository;
import com.environment.infrastructure.utils.ResponseWith;
import com.environment.domain.User;
/**
 * atualiza um usuário
 */
@RestController
public  class UpdateUser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);
    
    @PutMapping("/api/v1/user/{uuid}")
    public ResponseEntity<String> index(@PathVariable String uuid, @Valid @RequestBody String body) {
        try {                       
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();            
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            
            // preenche a classe do usuario
            User user = gson.fromJson(body, User.class);            
            
            // recebe e trata as violacoes
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            for (ConstraintViolation<User> violation : violations) {                
                Type errorType = new TypeToken<Map<String, String>>() {}.getType();
                return ResponseWith.json(gson.fromJson(violation.getMessage(), errorType));
            }

            user.setUuid( uuid );

            // valida o usuario
            Map<String, String> validateFirstName = user.validateFirstName();
            if (validateFirstName.get("status") != "200") {
                return ResponseWith.json(validateFirstName);
            }

            Map<String, String> validateLastName = user.validateLastName();
            if (validateLastName.get("status") != "200") {
                return ResponseWith.json(validateLastName);
            }

            Map<String, String> validatePhone = user.validatePhone();
            if (validatePhone.get("status") != "200") {
                return ResponseWith.json(validatePhone);
            }            
            
            // monta o fullName
            user.fullName = user.firstName+" "+user.lastName;
            
            // salvando no banco de dados
            UserRepository userRepository = new UserRepository();
            if (!userRepository.update(user)) {
                LOGGER.error("erro ao atualizar usuario");
                return ResponseWith.json(ResponseWith.map("500", "ONUXXX", "Não foi possível atualizar o usuário na base de dados!"));
            }
            
            // resposta de sucesso quando o usuario e criado
            return ResponseWith.json(ResponseWith.result("204", ""));

        } catch (Exception e) {
            LOGGER.error("erro ao processar criacao do usuario", e);
            return ResponseWith.json(ResponseWith.error("ONUXXX"));
        }
    }
}
