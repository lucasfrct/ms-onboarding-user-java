package com.environment.aplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Healthz {
    
    @RequestMapping("/healthz")
    public String index() {
        return "Health: UP 06";
    }
    
}
