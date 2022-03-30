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
    String sault;
    String uidDevice;

    public User(String uuid, String firstName, String lastName, String phone, String email, String password, String sault) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.sault = sault;
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
