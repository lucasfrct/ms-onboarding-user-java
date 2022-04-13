package com.environment.domain;

import com.environment.infrastructure.utils.SHA512;

import javax.validation.constraints.Size;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
/**
 * Classe responsável à definir e gerenciar o usuário
 * 
 */
public class User {

    String uuid;

    @Size(min = 3, max = 30, message = "{'status': '400', 'code': 'ONU008', 'message': 'Nome deve conter entre 3 e 30 caracteres!'}")
    @NotEmpty(message = "{'status': '400', 'code': 'ONU009', 'message': 'Nome nao pode ser vazio!'}")
    public String firstName;
    
    @Size(min = 3, max = 30, message = "{'status': '400', 'code': 'ONU010', 'message': 'Sobrenome deve conter entre 3 e 30 caracteres!'}")
    @NotEmpty(message = "{'status': '400', 'code': 'ONU011', 'message': 'Sobrenome nao pode ser vazio!'}")
    public String lastName;

    public String fullName;
    
    @Size(min = 11, max = 15, message = "{'status': '400', 'code': 'ONU012', 'message': 'Numero deve ter o seguinte formato: (xx) xxxxx-xxxx'}")
    String phone;

    @Email(message = "{'status': '400', 'code': 'ONU013', 'message': 'Email invalido!'}")
    String email;

    @Size(min = 8, max = 20, message = "{'status': '400', 'code': 'ONU014', 'message': 'password deve conter no minimo 8 e no maximo 20 caracteres!'}")
    String password;
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

    public Map<String, String> validateFirstName() {

        Map<String, String> valid = new HashMap<String, String>();

        Boolean check = this.firstName.matches(".*[0-9].*");
        if (check) {
            valid.put("status", "400");
            valid.put("code", "ONU001");
            valid.put("message", "First Name contém caracteres inválidos!");
            return valid;
        }

        valid.put("status", "200");
        return valid;
    }

    public Map<String, String> validateLastName() {

        Map<String, String> valid = new HashMap<String, String>();

        Boolean check = this.lastName.matches(".*[0-9].*");
        if (check) {
            valid.put("status", "400");
            valid.put("code", "ONU002");
            valid.put("message", "Last Name contém caracteres inválidos!");
            return valid;
        }

        valid.put("status", "200");
        return valid;
    }

    /**
     * verifica se o telefone tem algum caractere invalido ou se e diferente do tamanho 11.
     */
    public Map<String, String> validatePhone() {

        Map<String, String> valid = new HashMap<String, String>();

        // verifica se o telefone tem algum caractere invalido
        Boolean check = this.phone.matches(".*[a-zA-Z].*");
        if (check) {
            valid.put("status", "400");
            valid.put("message", "Phone contém caractéres inválidos!");
            valid.put("code", "ONU003");
            return valid;
        }

        // verifica se o telefone tem tamanho diferente de 11
        String num = this.phone.replaceAll("[^0-9]", "");
        int size = num.length();
        if (size != 11) {
            valid.put("status", "400");
            valid.put("message", "Phone tem tamanho inválido!");
            valid.put("code", "ONU004");
            return valid;
        }

        valid.put("status", "200");
        return valid;
    }

    public Map<String, String> validatePassword() {

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.password);

        Map<String, String> valid = new HashMap<String, String>();

        Boolean check = this.passwordCheck();
        if (!check) {
            valid.put("status", "400");
            valid.put("code", "ONU005");
            valid.put("message", "Senhas incompativeis");
            return valid;
        }

        Boolean checkRegex = matcher.matches();
        if (!checkRegex) {
            valid.put("status", "400");
            valid.put("code", "ONU006");
            valid.put("message", "O password deve conter no minimo 8 e no maximo 20 caracteres, pelo menos uma letra maiúscula, pelo menos um letra minúscula e um caractere especial!");
            return valid;
        }

        valid.put("status", "200");
        return valid;
    }

    public Map<String, String> validateUuid() {

        Map<String, String> valid = new HashMap<String, String>();

        Boolean check = this.uuid.length() == 36;
        if (!check) {
            valid.put("status", "400");
            valid.put("code", "ONU007");
            valid.put("message", "Serviço indisponível. Tente novamente em instantes.");
            return valid;
        }
        
        valid.put("status", "200");
        return valid;
    }


    public Map<String, String> validate() {

        Map<String, String> firstNameValid = this.validateFirstName();
        if (Integer.parseInt(firstNameValid.get("status")) >= 400) {
            return firstNameValid;
        }

        Map<String, String> lastNameValid = this.validateLastName();
        if (Integer.parseInt(lastNameValid.get("status")) >= 400) {
            return lastNameValid;
        }

        Map<String, String> phoneValid = this.validatePhone();
        if (Integer.parseInt(phoneValid.get("status")) >= 400) {
            return phoneValid;
        }

        Map<String, String> passwordValid = this.validatePassword();
        if (Integer.parseInt(passwordValid.get("status")) >= 400) {
            return passwordValid;
        }

        Map<String, String> uuidValid = this.validateUuid();
        if (Integer.parseInt(uuidValid.get("status")) >= 400) {
            return uuidValid;
        }
        
        Map<String, String> result = new HashMap<String, String>();
        result.put("status", "201");
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
