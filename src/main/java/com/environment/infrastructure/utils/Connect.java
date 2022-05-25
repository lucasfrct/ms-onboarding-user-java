package com.environment.infrastructure.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.lang.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
// import com.mongodb.client.result.InsertOneResult;



public class Connect {

    String databaseName = "onboarding_user";
    String collectionName = "onboarding_user";

    MongoDatabase database;
    MongoCollection<Document> collection;

    private static final Logger LOGGER = LoggerFactory.getLogger(Connect.class);

    public Connect() {
        try {
            this.getDatabase(this.databaseName);
            // this.getCollection(this.collectionName);
            // this.insert();

            System.out.println("*Connect*: ");

            // return this;
            
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
            // return ResponseWith.map(e.getStatus(), e.getCode(), e.getMessage());
        }
    }

    public MongoClient client() {
        try {
            MongoClientURI connectionString = new MongoClientURI(this.connectString());
            MongoClient mongoClient = new MongoClient(connectionString);
            return mongoClient;
            
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
            MongoClient mongoClient = new MongoClient();
            return mongoClient;

        }
    }

    public MongoDatabase getDatabase(String database) {
        try {
            MongoClient mongoClient = this.client();
            return this.database = mongoClient.getDatabase(database);
            
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
            Error err = new Error("{ \"status\": \"404\", \"code\": \"ONU019\", \"message\": \"nao foi possivel acessar o banco de dados\" }");
            return this.database;
            
        }
    }

    public String connectString() {
        return "mongodb://development:Alterar123@127.0.0.1:27017";
    }

    public MongoCollection<Document> getCollection(String collection) {
        try {
            return this.collection = this.database.getCollection(collection);
            
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
            new Error("colecao nao encontrada");
            return this.collection;
        }
    }
    
    public String health() {
        // try {
        //     Document hello = new Document().append("serverStatus", 1);    
        //     Document result = this.database.runCommand( hello );
    
        //     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        //     String dateTime = formatter.format(result.get("localTime"));
            
        //     return dateTime;
            
        // } catch (Exception e) {
        //     LOGGER.error("exception: "+e);
        //     return "dateTime";
        // }

        return "UP connect";
    }

    public void insert(Map<String, String> documentMap) {
        try {
            // this.collection.insertOne(new Document("name", "marcus FEIO!!!!"));
            // this.collection.insertOne(new BasicDBObject(documentMap));
    
            // try {
            //         this.collection.insertOne(new Document()
            //         .append("_id", new ObjectId())
            //         .append("title", "Ski Bloopers")
            //         .append("genres", Arrays.asList("Documentary", "Comedy")));
            //     } catch (MongoException me) {
            //         System.err.println("Unable to insert due to an error: " + me);
            // }
            
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
        }
    }
 
}

// conn = new Connect();
// db = conn.mongo();