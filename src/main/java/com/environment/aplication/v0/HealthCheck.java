package com.environment.aplication.v0;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.environment.infrastructure.utils.Connect;


@RestController
public class HealthCheck {
    
    @RequestMapping("/mongo/health")
    public String index() {
        Connect connect = new Connect();
        String currentdate = connect.healthCheck();
        return "UP: "+currentdate;
    }
    
}

