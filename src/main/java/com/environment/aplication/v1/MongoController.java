package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.environment.infrastructure.utils.Connect;

/**
 * Health Check do mongo DB 
 */

@RestController
public class MongoController {
    
    @GetMapping("/api/v1/mongo/health")
    public String index() throws Exception {

        Connect conn = new Connect();
        return conn.health();
    }
}
