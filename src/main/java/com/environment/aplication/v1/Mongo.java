package com.environment.aplication.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Health Check do mongo DB 
 */

@RestController
public class Mongo {
    
    @GetMapping("/api/v1/mongo/health")
    public String index() {
        return "Health";
    }
}
