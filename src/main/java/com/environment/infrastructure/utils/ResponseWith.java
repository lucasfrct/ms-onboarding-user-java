package com.environment.infrastructure.utils;

import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseWith {

    public ResponseWith(Map<String, String> response) {

    }

    // response = new ResponseWith(map)
    // handleStatus(int status)
    //      return HttpStatus.BAD_REQUEST   (400)
    //      return HttpStatus.OK            (200)
    //      return HttpStatus.NOT_FOUND     (404)
    public HttpStatus handleStatus(Map<String, String> response) {
        int statusNum = Integer.parseInt(response.get("status"));
        if (statusNum >= 100 && statusNum < 200) {
            return HttpStatus.CONTINUE;
        }

        if (statusNum >= 200 && statusNum < 300) {
            return HttpStatus.CREATED;
        }

        if (statusNum >= 300 && statusNum < 400) {
            return HttpStatus.MULTIPLE_CHOICES;
        }

        if (statusNum >= 400 && statusNum < 500) {
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ResponseEntity response(Map<String, String> validate) {

        return ResponseEntity.status(handleStatus(validate)).body(handleMessage(validate.get("code"), validate.get("message")));
    }

    // { status = 200; code = ONU000; message = "" }
    // handleMessage(String code, String message)
    //      return { "code": "ONU", "message": "" } (JSON)
    public String handleMessage(String code, String message) {
        return null;
    }
}

