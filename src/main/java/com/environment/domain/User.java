package com.environment.domain;

/**
 * Classe responsável à definir e gerenciar o usuário
 * 
 */
public class User {

    String uuid;
    String firstName;
    String lastName;
    String fullName;
    String phone;
    String email;
    String password;
    String confirmPassword;
    String salt;
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

    public void setSalt(String salt) {
        this.salt = salt;
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
        System.out.println("firstName: "+this.lastName);
        return "";
    }

}
