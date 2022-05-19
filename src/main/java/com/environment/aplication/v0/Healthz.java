package com.environment.aplication.v0;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Healthz {
    
    @RequestMapping("/healthz")
    public String index() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentdate = formatter.format(date);
        return "UP: "+currentdate;
    }
    
}