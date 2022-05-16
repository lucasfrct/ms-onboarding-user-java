package com.environment.infrastructure.utils;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.result.InsertOneResult;


public class Connect {

    public String dataBaseName = "onboarding_user";

    public String collectionName = "onboarding_user";

    MongoDatabase database;

    public Connect() {
        try (MongoClient mongoClient = MongoClients.create(this.connectString())) {
            System.out.println("Funfou!");
        }
       
    }

    public MongoCollection<Document> getCollection() {
        return this.database.getCollection(this.collectionName);
    }

    public String connectString() {
        return "mongodb://development:Alterar123@127.0.0.1:27017/?maxPoolSize=20&w=majority";
    }

    public void insert() {
        // try {
        //     MongoCollection<Document> collection = this.getCollection();
        //     InsertOneResult result = collection.insertOne(new Document()
        //             .append("_id", new ObjectId())
        //     System.out.println("Success! Inserted document id: " + result.getInsertedId());
        // } catch (MongoException me) {
        //     System.err.println("Unable to insert due to an error: " + me);
        // }
    }

    public String healthCheck() {
        // Document serverStatus = this.database.runCommand(new Document("serverStatus", 1));
        // System.out.println(serverStatus);
        // Map connections = (Map) serverStatus.get("connections");
        // Integer current = (Integer) connections.get("current");

        // return String.valueOf(current);
        return "UP";

    }
 
}
// MongoClient mongoClient = new MongoClient();
// MongoDatabase database = mongoClient.getDatabase("admin");
// Document serverStatus = database.runCommand(new Document("serverStatus", 1));
// Map connections = (Map) serverStatus.get("connections");
// Integer current = (Integer) connections.get("current");