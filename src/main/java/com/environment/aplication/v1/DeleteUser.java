package com.environment.aplication.v1;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * Deleta uma usuário com base no UUID
 */
@RestController
public class DeleteUser {
    
    @DeleteMapping("/api/v1/user/{uuid}")	
    public String index( String uuid) {
        return "DELETE USER";
    }
}
