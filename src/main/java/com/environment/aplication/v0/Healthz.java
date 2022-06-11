package com.environment.aplication.v0;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

import com.environment.infrastructure.utils.ResponseWith;

import java.text.SimpleDateFormat;

import java.util.Date;

@RestController
public class Healthz {
    
    @RequestMapping("/healthz")
    public ResponseEntity<String> index() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentdate = formatter.format(date);

        return ResponseWith.json(ResponseWith.result("200", "UP: "+currentdate));
    }
    
}