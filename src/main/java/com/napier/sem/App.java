package com.napier.sem;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        // Connect to MongoDB on local system using port 27000
        MongoClient mongoClient = MongoClients.create("mongodb://mongo-dbserver");
        // Get a database - will create when we use it
        MongoDatabase database = mongoClient.getDatabase("mydb");
        // Get a collection from the database
        MongoCollection<Document> collection = database.getCollection("test");
        // Create a document to store
        Document document = new Document("name", "Devyon Lozano")
                .append("class", "DevOps")
                .append("year", "2025")
                .append("result", new Document("CW", 95).append("EX", 85));
        // Add document to collection
        collection.insertOne(document);

        // Check document in collection
        Document myDocument = collection.find().first();
        System.out.println(myDocument.toJson());
    }
}
