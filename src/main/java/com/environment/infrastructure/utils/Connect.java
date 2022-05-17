package com.environment.infrastructure.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class Connect {

    String databaseName = "onboarding_user";
    String collectionName = "onboarding_user";

    MongoDatabase database;
    MongoCollection<Document> collection;

    public Connect() {
        this.getDatabase(this.databaseName);
        this.getCollection(this.collectionName);
        this.insert();
    }

    public MongoClient client() {
        MongoClientURI connectionString = new MongoClientURI(this.connectString());
        MongoClient mongoClient = new MongoClient(connectionString);
        return mongoClient;
    }

    public MongoDatabase getDatabase(String database) {
        MongoClient mongoClient =this.client();
        return this.database = mongoClient.getDatabase(database);
    }

    public String connectString() {
        return "mongodb://development:Alterar123@127.0.0.1:27017";
    }

    public MongoCollection<Document> getCollection(String collection) {
        return this.collection = this.database.getCollection(collection);
    }

    public void insert() {
        this.collection.insertOne(new Document("name", "marcus FEIO!!!!"));
    }

    public String healthCheck() {
        return "UP";
    }
 
}
