package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

import java.util.UUID;
import com.environment.infrastructure.utils.SHA512;

import com.environment.domain.User;

/**
 * Cria um novo usuario
 */
@RestController
public class CreateUser {    
    @PostMapping("/api/v1/user")
    public String index(@RequestBody String body) {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        // Preenche a classe user com body
        User user = gson.fromJson(body, User.class);

        // Gera um UUID aleatório
        user.setUuid(UUID.randomUUID().toString());

        // Gera um sault com tamanho de 6 caracteres
        user.setSalt(SHA512.salt(999999, 6));
        
        String password = new String(user.getPassword());
        Boolean check = password.matches(user.getConfirmPassword());
        if (!check) {
            return "Senhas incompativeis";
        }
        String cipher = SHA512.getSHA512(password, user.getSalt());


        System.out.println("req: "+cipher);

        user.extract();

        return "CREATE USER";
    }
    
}
