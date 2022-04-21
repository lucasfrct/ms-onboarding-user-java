package com.environment.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;

import com.environment.domain.User;

public class UserRepository {

    public User user;

    public UserRepository(User user) {

        this.user = user;

    };

    public Map<String, String> save() {

        Map<String, String> response = new HashMap<String, String>();
        
        return response;

    };
  
}
