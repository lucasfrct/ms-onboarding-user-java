package com.environment.infrastructure.utils;

import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWith {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseWith.class);

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    
    int statusNum;
    Map<String, String> response;
    
    public ResponseWith(Map<String, String> response) {
        try {
            if (!response.containsKey("status")) {
                throw new Exception("erro ao converter status");
            }

            this.statusNum = Integer.parseInt(response.get("status"));
            this.response = response;
            
        } catch (Exception e) {
            this.LOGGER.error("erro ao converter status", e);

            Map<String, String> valid = new HashMap<String, String>();

            valid.put("status", "500");
            valid.put("code", "ONU015");
            valid.put("message", "Serviço indisponível no momento, tente mais tarde!");

            this.statusNum = 500;
            this.response = valid;
        }
    }
    public HttpStatus handleStatus(int statusNum) {
        
        if (statusNum == 200) {
            return HttpStatus.OK;
        }
        
        if (statusNum == 201) {
            return HttpStatus.CREATED;
        }
        
        if (statusNum == 204) {
            return HttpStatus.NO_CONTENT;
        }
        
        if (statusNum == 400) {
            return HttpStatus.BAD_REQUEST;
        }
        
        if (statusNum == 403) {
            return HttpStatus.FORBIDDEN;
        }
        
        if (statusNum == 404) {
            return HttpStatus.NOT_FOUND;
        }
        
        if (statusNum == 408) {
            return HttpStatus.REQUEST_TIMEOUT;
        }
        
        if (statusNum >= 100 && statusNum < 200) {
            return HttpStatus.CONTINUE;
        }
        
        if (statusNum >= 200 && statusNum < 300) {
            return HttpStatus.CREATED;
        }
        
        if (statusNum >= 300 && statusNum < 400) {
            return HttpStatus.PERMANENT_REDIRECT;
        }
        
        if (statusNum >= 400 && statusNum < 500) {
            return HttpStatus.BAD_REQUEST;
        }
        
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public Object handleMessage(Map<String, String> response) {
        
        Object message = "";

        if (this.statusNum == 200) {

            if (response.containsKey("data")) {
                message = response.get("data");
            }
            return message;
        }

        if (this.statusNum == 201 || this.statusNum == 204) {
            return message;
        }

        if (this.statusNum >= 400) {

            Map<String, String> messageError = new HashMap<String, String>();

            if (response.containsKey("code")) {
                messageError.put("code", response.get("code"));
            }

            if (response.containsKey("message")) {
                messageError.put("message", response.get("message"));                
            }

            return messageError;
        }

        return message;
    }

    public ResponseEntity<String> json() {
        try {
            String body = gson.toJson(handleMessage(this.response));        
            return ResponseEntity.status(handleStatus(this.statusNum)).body(body);            

        } catch (Exception e) {
            this.LOGGER.error("erro ao converter para json", e);

            Map<String, String> valid = new HashMap<String, String>();

            valid.put("status", "500");
            valid.put("code", "ONU016");
            valid.put("message", "Serviço indisponível no momento, tente mais tarde!");

            this.statusNum = 500;
            this.response = valid;

            String body = gson.toJson(handleMessage(this.response));        
            return ResponseEntity.status(handleStatus(this.statusNum)).body(body);
        }
        
    }
}