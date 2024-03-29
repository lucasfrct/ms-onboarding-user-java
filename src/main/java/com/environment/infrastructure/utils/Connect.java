package com.environment.infrastructure.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.lang.String;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.excludeId;

public class Connect {

    String user = "development";
    String password = "Alterar123";
    String host = "127.0.0.1";
    String port = "27017";

    public String databaseName = "onboarding_user";
    public String collectionName = "onboarding_user";

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    public MongoCollection<Document> mongoCollection;
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
            this.error(400, "ONU021", "credencial password vazia");
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
            Object result = this.database(this.databaseName).runCommand(new Document().append("serverStatus", 1)).get("localTime");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = formatter.format(result);
        
            return String.format("UP %s", dateTime);
            
        } catch (Exception e) {
            LOGGER.error("Banco de dados indisponível, não é possível obter um client: ", e);

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentdate = formatter.format(date);

            return String.format("DOWN %s", currentdate);
        }
    }

    public Boolean insert(Document user) {
        try {
            this.mongoCollection.insertOne(user);
            
            ObjectId id = (ObjectId)user.get( "_id" );
            if (id.toString().isEmpty()) {
                return false;
            }
            
            return true;
                
        } catch (Exception e) {
            LOGGER.error("exception: ", e);
            System.err.println("Unable to insert due to an error: " + e);
            return false;
        }
    }

    public Boolean delete(Document query) {
        try {            
            DeleteResult deleteResult = this.mongoCollection.deleteOne(query);
            if (deleteResult.getDeletedCount() != 1) {
                return false;
            }

            return true;
            
        } catch (Exception e) {
            LOGGER.error("erro ao excluir usuario", e);
            return false;
        }
    }

    public Boolean update(BasicDBObject query, BasicDBObject fields) {
        try {
            UpdateResult updateResult = this.mongoCollection.updateMany(query, fields);
            if (updateResult.getModifiedCount() != 1) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String readOne(Document query, Bson includes) {
        try {
            Document data = this.mongoCollection.find(query).projection(fields(excludeId(), includes)).first();
            return data.toJson();
        } catch (Exception e) {
            System.err.println("Error: "+ e);
            return new Document().toJson();
        }
    }

    public String readAll(Document query, Bson includes, int start, int limit) {
        try {
            ArrayList<String> response = new ArrayList<>();
            int i = 1;            

            FindIterable<Document> iterDoc = this.mongoCollection.find(query).projection(fields(excludeId(), includes));
            MongoCursor<Document> cursor = iterDoc.iterator();
            while (cursor.hasNext() && i < (start + limit) ) {
                if (i >= start) { response.add(cursor.next().toJson()); }
                if (i >= (start + limit)) { break; }
                i++;
            }
            
            return response.toString();
        } catch (Exception e) {
            System.err.println("Error: "+ e);
            return new Document().toJson();
        }
    }
}