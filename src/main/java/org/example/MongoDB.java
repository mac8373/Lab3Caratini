/**
 * Project: Lab3Caratini
 * Purpose Details: This project demonstrates CRUD for MongoDB.
 * Course: IST242
 * Author: Maximo Caratini
 * Date Developed: 2024-02-15
 * Last Date Changed: 2024-02-29
 * Rev: 1.0
 */
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class MongoDB {
    public static void main(String[] args) {
        // Create a MongoClient using the factory method
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Access the database and collection
            MongoDatabase database = mongoClient.getDatabase("your_database_name");
            MongoCollection<Document> collection = database.getCollection("customers");

            // Insert a document
            Document newCustomer = new Document("first_name", "John")
                    .append("last_name", "Doe")
                    .append("age", 20)
                    .append("email", "john@example.com");
            collection.insertOne(newCustomer);

            // Read
            FindIterable<Document> customers = collection.find();
            for (Document customer : customers) {
                System.out.println(customer.toJson());
            }

            // Update
            Document updatedCustomer = new Document("$set", new Document("last_name", "Doe")
                    .append("age", 20)
                    .append("email", "john@example.com"));
            collection.updateOne(new Document("first_name", "John"), updatedCustomer);

            // Read again
            customers = collection.find();
            for (Document customer : customers) {
                System.out.println(customer.toJson());
            }

            // Delete
            collection.deleteOne(new Document("first_name", "John"));
            System.out.println("Deleted document with first_name 'John'");
            }
        }
    }
