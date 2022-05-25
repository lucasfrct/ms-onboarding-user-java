package com.environment.aplication.v0;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.environment.infrastructure.utils.Connect;
import org.springframework.http.ResponseEntity;

import com.environment.infrastructure.utils.ResponseWith;

import java.util.Map;
import org.slf4j.Logger;
import java.util.HashMap;
import org.slf4j.LoggerFactory;

@RestController
public class HealthCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheck.class);
    
    @RequestMapping("/mongo/health")
    public ResponseEntity<String> index() {
        try {
            Map<String, String> response = new HashMap<String, String>();
            
            Connect connect = new Connect();

            System.out.println("Heath** "+connect);

            // String status = connect.healthCheck();

            response.put("status", "200");
            response.put("data", "UP: "); 

            return ResponseWith.json(response);
            
        } catch (Exception e) {
            this.LOGGER.error("erro ao processar criacao do usuario", e);

            Map<String, String> response = ResponseWith.map(
                "500", 
                "ONU019", 
                "Serviço indisponível no momento, tente mais tarde!"
            );
            return ResponseWith.json(response);
        }
    }    
}