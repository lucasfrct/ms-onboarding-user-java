package com.environment.aplication.v0;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.environment.infrastructure.utils.Connect;
import org.springframework.http.ResponseEntity;

import com.environment.infrastructure.utils.ResponseWith;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HealthCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheck.class);
    
    @RequestMapping("/mongo/health")
    public ResponseEntity<String> index() {
        try {
            Map<String, String> response = new HashMap<String, String>();
            // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            
            Connect connect = new Connect();
            String status = connect.healthCheck();

            // Date date = new Date();
            // System.out.println("")
            // thu may 19 23:27:46
            // 2022-05-19 23:27:46

            response.put("status", "200");
            response.put("data", "UP: "+status); 

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

