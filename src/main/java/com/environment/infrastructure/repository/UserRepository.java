package com.environment.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

import com.environment.domain.User;
import com.environment.infrastructure.utils.ResponseWith;
import com.environment.infrastructure.utils.Connect;

@Repository
public class UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public User user;
    public Map<String, String> userData = new HashMap<String, String>();

    public UserRepository() {
    };

    public Boolean save(User user) {

        this.user = user;

        try {
            Map<String, String> userRepository = new HashMap<String, String>();
    
            userRepository.put( "passwordHash" , user.getPasswordHash());
            userRepository.put( "firstName" , user.getFirstName());
            userRepository.put( "lastName" , user.getLastName());
            userRepository.put( "fullName" , user.getFullName());
            userRepository.put( "phone" , user.getPhone());
            userRepository.put( "email" , user.getEmail());
            userRepository.put( "uuid" , user.getUuid());
            userRepository.put( "salt" , user.getSalt());
    
            String body = gson.toJson(userRepository);
    
            Connect connect = new Connect();
            connect.insert(body);
    
            return true;

            // this.userData = userRepository;

            // Map<String, String> result = new HashMap<String, String>();
            // result.put("status", "201");
            // return result;
            
        } catch (Exception e) {
            LOGGER.error("erro ao salvar usuario", e);
            return false;
            // return ResponseWith.map("500", "ONU018", "Serviço indisponível no momento, tente mais tarde!");
        }


    };
  
}
