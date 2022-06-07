package com.environment.infrastructure.repository;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

import java.text.SimpleDateFormat;

import com.mongodb.BasicDBObject;
import static com.mongodb.client.model.Projections.include;

import com.environment.domain.User;
import com.environment.infrastructure.utils.Connect;
import com.environment.infrastructure.utils.ResponseWith;

@Repository
public class UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public User user;
    public Map<String, String> userData = new HashMap<String, String>();

    public UserRepository() {
    };

    public Map<String, String> save(User user) {        
        try {
            this.user = user;

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentdate = formatter.format(date);

            Document insertFields = new Document();
    
            insertFields.append( "passwordHash" , user.getPasswordHash());
            insertFields.append( "firstName" , user.getFirstName());
            insertFields.append( "lastName" , user.getLastName());
            insertFields.append( "fullName" , user.getFullName());
            insertFields.append( "phone" , user.getPhone());
            insertFields.append( "email" , user.getEmail());
            insertFields.append( "uuid" , user.getUuid());
            insertFields.append( "salt" , user.getSalt());
            insertFields.append( "createAt" , currentdate);
            insertFields.append( "updateAt" , currentdate);
        
            Connect connect = new Connect();
            connect.database("");
            connect.collection("");

            if(!connect.insert(insertFields)) {
                return ResponseWith.map("500", "ONUXXX", "Não foi possível inserir usuário.");
            }
    
            return ResponseWith.result("201", "");            
            
        } catch (Exception e) {
            LOGGER.error("erro ao salvar usuario", e);
            return ResponseWith.error("ONUXXX");
        }
    };

    public Map<String, String> delete(User user) {
        try {

            Connect connect = new Connect();
            connect.database("");
            connect.collection("");
            
            if (!connect.delete(new Document("uuid", user.getUuid()))) {
                return ResponseWith.map("404", "ONUXXX", "Não foi possível encontrar usuário.");
            }
            
            return ResponseWith.result("204", "");
            
        } catch (Exception e) {
            LOGGER.error("erro ao excluir usuario", e);
            return ResponseWith.error("ONUXXX");
        }
    };

    public Boolean update(User user) {        
        try {            
            this.user = user;

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentdate = formatter.format(date);

            BasicDBObject updateFields = new BasicDBObject();
            updateFields.append( "firstName" , user.getFirstName() );
            updateFields.append( "lastName" , user.getLastName());            
            updateFields.append( "fullName" , user.getFullName());           
            updateFields.append( "phone" , user.getPhone());            
            updateFields.append( "email" , user.getEmail());
            updateFields.append( "updateAt" , currentdate);

            BasicDBObject fields = new BasicDBObject();
            fields.append("$set", updateFields);    
    
            Connect connect = new Connect();
            connect.database("");
            connect.collection("");

            BasicDBObject query = new BasicDBObject("uuid",  user.getUuid());

            connect.update(query, fields);
    
            return true;            
            
        } catch (Exception e) {
            LOGGER.error("erro ao salvar usuario", e);
            return false;
        }
    };

    public Map<String, String> readByUuid(User user) {
        try {
            Connect connect = new Connect();
            connect.database("");
            connect.collection("");

            Document query = new Document("uuid", user.getUuid());

            Bson includes = include("firstName", "lastName", "fullName", "phone", "email");

            String result = connect.readOne(query, includes);
            
            return ResponseWith.result("200", result);
            
        } catch (Exception e) {
            LOGGER.error("erro ao excluir usuario", e);
            return ResponseWith.error("ONUXXX");
        }
    };
}