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
    
    @RequestMapping("/mongo/health")
    public ResponseEntity<String> index() {
        try {            
            Connect connect = new Connect();
            return ResponseWith.json(ResponseWith.result("200", connect.health()));
            
        } catch (Exception e) {
            LOGGER.error("banco de dados indisponivel.", e);            
            return ResponseWith.json(ResponseWith.error("ONU019"));
        }
    }    
}