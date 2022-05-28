package com.environment.infrastructure.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import org.aspectj.weaver.ast.Or;
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
import java.lang.String;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoException;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
// import com.mongodb.client.result.InsertOneResult;



public class Connect {

    String user = "development";
    String password = "Alterar23";
    String host = "127.0.0.1";
    String port = "27017";

    String databaseName = "onboarding_user";
    String collectionName = "onboarding_user";

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    String connectstring = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(Connect.class);

    public Connect() {}

    public String connectString() {
        return this.connectstring = "mongodb://development:Alterar123@127.0.0.1:27017";
    }

    public MongoClient client() throws Exception {

        if(this.user.isEmpty()) {
            this.error(400, "ONU020", "credencial de usuário vazia");
        }

        if(this.password.isEmpty()) {
            this.error(400, "ONU021", "ccredencial password vazia");
        };

        if(this.host.isEmpty()) {
            this.error(400, "ONU022", "O host não foi definido");
        }

        if(this.port.isEmpty()) {
            this.error(400, "ONU023", "a porta do database não foi definida");
        }

        if(this.connectstring.isEmpty()) {

            this.connectString();

            if(this.connectstring.isEmpty()) {
                this.error(400, "ONU024", "URL de conexao invalida");
            }
        }

        MongoClientURI connectionString = new MongoClientURI(this.connectstring);
        return this.mongoClient = new MongoClient(connectionString);
    }

    public MongoDatabase database(String database) throws Exception {

        if(!database.isEmpty()) {
            this.databaseName = database;
        }

        this.client();

        if(this.databaseName.isEmpty()) {
            this.error(400, "ONU25", "O nome do database não foi definido");
        }

        return this.mongoDatabase = this.mongoClient.getDatabase(this.databaseName);
    }

    public MongoCollection<Document> collection(String collection) throws Exception {

        if(!collection.isEmpty()) {
            this.collectionName = collection;
        }

        if(this.collectionName.isEmpty()) {
            this.error(400, "ONU026", "O nome da coleção não foi definido.");
        }

        if(this.mongoDatabase.getName().isEmpty()) {
            this.error(400, "ONU027", "O banco de dados não foi definido");
        }

        return this.mongoCollection = this.mongoDatabase.getCollection(this.collectionName);
            
    }

    public void error(int status, String code, String message) throws Exception {
        Exception e = new Exception(String.format("{ status: %s, code: %s, message: %s }", status, code, message));
        throw e;
    }
    
    public String health() throws Exception {
        try {
        
            this.client();
            this.database(this.databaseName);
            this.collection(this.collectionName);

            Document statusDB = new Document().append("serverStatus", 1);    
            Document result = this.mongoDatabase.runCommand(statusDB);
    
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            String dateTime = formatter.format(result.get("localTime"));
        
            return "UP "+dateTime;
            
        } catch (Exception e) {
            LOGGER.error("Banco de dados indisponível, não possível obter um client: ", e);
            return e.getMessage();
        }

    }

    public void insert(String user) {
        try {

            Document dbObject = Document.parse(user);
            this.mongoCollection.insertOne(new Document(dbObject));
            // this.mongoCollection.insert(new BasicDBObject(documentMap));

            // this.collection.insertOne(new Document("name", "marcus FEIO!!!!"));
            // this.collection.insertOne(new BasicDBObject(documentMap));           
                
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
            System.err.println("Unable to insert due to an error: " + e);
        }
    }
 
}

// conn = new Connect();
// db = conn.mongo();