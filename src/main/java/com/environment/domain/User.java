package com.environment.domain;

import com.environment.infrastructure.utils.SHA512;
import javax.validation.constraints.Size;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.environment.infrastructure.utils.RemoveChar;

/**
 * Classe responsável à definir e gerenciar o usuário
 * 
 */
public class User {

    String uuid;

    @Size(min = 3, max = 30, message = "firstName must be between 3 and 30 characters")
    @NotEmpty(message = "firstName cannot be null")
    private String firstName; // min 3 - max 30
    
    @Size(min = 3, max = 30, message = "lastName must be between 3 and 30 characters")
    @NotEmpty(message = "lastName cannot be null")
    String lastName; // min 3 - max 30

    String fullName;
    
    @Size(min = 11, max = 15, message = "phone must be 11 characters long")
    @RemoveChar
    String phone; // length 11, Só numeros

    @Email
    String email; // alphanumerico, has @, has .com


    String password; // uppercase, lowercase, num, special, min 8
    String confirmPassword;
    String salt;
    String passwordHash;
    String uidDevice;
    
    public User(String uuid, String firstName, String lastName, String phone, String email, String password, String confirmPassword, String salt) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.salt = salt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSalt() {
        return salt;
    }

    public String setSalt(String salt) {
        return this.salt = salt;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * verifica se o telefone tem algum caractere invalido ou se e diferente do tamanho 11.
     */
    public Map<String, String> validatePhone() {

        Map<String, String> response = new HashMap<String, String>();

        // verifica se o telefone tem algum caractere invalido
        Boolean check = this.phone.matches(".*[a-zA-Z].*");
        if (check) {
            response.put("status", "false");
            response.put("message", "Phone contém caractéres inválidos!");
            return response;
        }

        // verifica se o telefone tem tamanho diferente de 11
        String num = this.phone.replaceAll("[^0-9]", "");
        int size = num.length();
        if (size != 11) {
            response.put("status", "false");
            response.put("message", "Phone tem tamanho inválido!");
            return response;
        }

        response.put("status", "true");
        response.put("message", "");
        return response;
    }
    // [status = true, message = "message"]

    public Map<String, String> validate() {
        Map<String, String> phone = this.validatePhone();
        Boolean checkPhone = Boolean.parseBoolean(phone.get("status"));
        if (!checkPhone) {
            return phone;
        }
        
        Map<String, String> result = new HashMap<String, String>();
        result.put("status", "true");
        result.put("message", "");
        return result;
    }

    public String save() {
        return "";
    }

    public String update() {
        return "";
    }

    public String list() {
        return "";
    }

    public String delete() {
        return "";
    }

    public String extract() {
        System.out.println("firstName: "+this.firstName);
        return "";
    }

    public String passwordHash() {
        // Gera um salt com tamanho de 6 caracteres
        this.salt = SHA512.salt(999999, 6);

        // Gera uma hash do password com o salt
        this.passwordHash = SHA512.cipher(this.password, this.salt);
        return this.passwordHash;
    }

    public Boolean passwordCheck() {
        return this.password.matches(this.confirmPassword);
    }

}
