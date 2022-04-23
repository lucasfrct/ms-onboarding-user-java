package com.environment.domain;

import com.environment.infrastructure.utils.ResponseWith;
import com.environment.infrastructure.utils.SHA512;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);


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
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    
    public String getSalt() {
        return salt;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> validateFirstName() {

        Map<String, String> valid = new HashMap<String, String>();

        Boolean check = this.firstName.matches(".*[0-9].*");
        if (check) {
            this.LOGGER.error("first name contem caracteres invalidos!", check);
            return ResponseWith.map("400", "ONU001", "First Name contém caracteres inválidos!");
        }

        valid.put("status", "200");
        return valid;
    }

    public Map<String, String> validateLastName() {
        
        Boolean check = this.lastName.matches(".*[0-9].*");
        if (check) {
            this.LOGGER.error("last name contem caracteres invalidos!", check);
            return ResponseWith.map("400", "ONU002", "Last Name contém caracteres inválidos!");
            
        }

        Map<String, String> valid = new HashMap<String, String>();
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
            this.LOGGER.error("phone contem caracteres invalidos!", check);
            return ResponseWith.map("400", "ONU003", "Phone contém caracteres inválidos!");

        }

        // verifica se o telefone tem tamanho diferente de 11
        String num = this.phone.replaceAll("[^0-9]", "");
        int size = num.length();
        if (size != 11) {
            this.LOGGER.error("phone tem tamanho invalido!", size);
            return ResponseWith.map("400", "ONU004", "Phone tem tamanho inválido!");

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
            this.LOGGER.error("senhas incompativeis", check);
            return ResponseWith.map("400", "ONU005", "Senhas incompativeis");

        }

        Boolean checkRegex = matcher.matches();
        if (!checkRegex) {
            this.LOGGER.error("o password deve conter no minimo 8 e no maximo 20 caracteres, pelo menos uma letra maiuscula, pelo menos um letra minuscula e um caractere especial!", checkRegex);
            return ResponseWith.map("400", "ONU006", "O password deve conter no minimo 8 e no maximo 20 caracteres, pelo menos uma letra maiúscula, pelo menos um letra minúscula e um caractere especial!");

        }

        valid.put("status", "200");
        return valid;
    }

    public Map<String, String> validateUuid() {

        Map<String, String> valid = new HashMap<String, String>();

        Boolean check = this.uuid.length() == 36;
        if (!check) {
            this.LOGGER.error("servico indisponivel. tente novamente em instantes.", check);
            return ResponseWith.map("400", "ONU007", "Serviço indisponível. Tente novamente em instantes.");

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
        result.put("status", "200");
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
