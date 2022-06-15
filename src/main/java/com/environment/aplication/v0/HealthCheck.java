package com.environment.aplication.v0;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.environment.infrastructure.utils.Connect;
import org.springframework.http.ResponseEntity;

import com.environment.infrastructure.utils.ResponseWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HealthCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheck.class);
    
    @RequestMapping("/api/v0/mongo/health")
    public ResponseEntity<String> index() {
        try {            
            Connect connect = new Connect();
            String result = connect.health();

            if (result.contains("DOWN")) {
                return ResponseWith.json(ResponseWith.map("500", "ONU044", result));
            }

            return ResponseWith.json(ResponseWith.result("200", result));
            
        } catch (Exception e) {
            LOGGER.error("banco de dados indisponivel.", e);            
            return ResponseWith.json(ResponseWith.error("ONU019"));
        }
    }    
}