package com.environment.domain;

import com.environment.infrastructure.utils.SHA512;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
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
    
    @Size(min = 11, max = 11, message = "phone must be 11 characters long")
    @RemoveChar
    String phone; // length 11

    @Email
    String email; // alphanumerico, has @, has .com


    String password; // uppercase, lowercase, num, special, min 8
    String confirmPassword;
    String salt;
    String passwordHash;
    String uidDevice;
    
    public User(String uuid, String firstName, String lastName, CharSequence phone, String email, String password, String confirmPassword, String salt) {
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

    public Boolean validate() {
        return true;
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
