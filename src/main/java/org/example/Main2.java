/**
 * Project: Lab3Caratini
 * Purpose Details: This project demonstrates CRUD on MySQL, MongoDB, Redis, and uses a blockchain for customer data.
 * Course: IST242
 * Author: Maximo Caratini
 * Date Developed: 2024-02-15
 * Last Date Changed: 2024-02-29
 * Rev: 1.0
 */

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import redis.clients.jedis.Jedis;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class Main2 {
    private static final Logger logger = LoggerFactory.getLogger(Main2.class);

    public static void main(String[] args) {
        SimpleBlockchain blockchain = new SimpleBlockchain();

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            // Main menu display
            System.out.println("Menu:");
            System.out.println("1. Handle SQL");
            System.out.println("2. Handle MongoDB");
            System.out.println("3. Handle Redis");
            System.out.println("4. Handle Blockchain");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Reading user choice
            choice = scanner.nextInt();
            scanner.nextLine();

            // Handling user choice
            switch (choice) {
                case 1:
                    handleSQL();
                    break;
                case 2:
                    handleMongoDB();
                    break;
                case 3:
                    handleRedis();
                    break;
                case 4:
                    handleBlockchain();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        } while (choice != 5);

        // Closing scanner
        scanner.close();

        // Adding customer blocks to blockchain
        SimpleBlockchain.Customer customer1 = new SimpleBlockchain.Customer (1, "John", "Doe", 30, "john@example.com");
        blockchain.addBlock(customer1, "Transaction 1");

        SimpleBlockchain.Customer customer2 = new SimpleBlockchain.Customer(2, "Jane", "Smith", 25, "jane@example.com");
        blockchain.addBlock(customer2, "Transaction 2");

    }

    private static void handleSQL() {
        System.out.println("Handling SQL...");
        MySQLDatabase.main(new String[]{});
    }

    private static void handleMongoDB() {
        System.out.println("Handling MongoDB...");
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("your_database_name");
            MongoCollection<Document> collection = database.getCollection("customers");

            // Perform MongoDB operations
            performMongoDBOperations(collection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void performMongoDBOperations(MongoCollection<Document> collection) {
        try {
            // Create a new customer document
            Document newCustomer = new Document("first_name", "John")
                    .append("last_name", "Doe")
                    .append("age", 20)
                    .append("email", "john@example.com");
            // Insert new customer doc
            collection.insertOne(newCustomer);
            System.out.println("Document inserted: " + newCustomer.toJson());

            // Read and print only the created document
            printFilteredDocuments(collection, "first_name", "John");

            // Update
            Document updatedCustomer = new Document("$set", new Document("first_name", "John"));
            collection.updateOne(new Document("first_name", "John"), updatedCustomer);
            System.out.println("Document updated: " + updatedCustomer.toJson());

            // Read again after update
            printFilteredDocuments(collection, "first_name", "John");

            // Delete
            collection.deleteOne(new Document("first_name", "John"));
            System.out.println("Document deleted with first_name 'John'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void printFilteredDocuments(MongoCollection<Document> collection, String field, String value) {
        Document filter = new Document(field, value);
        FindIterable<Document> filteredCustomers = collection.find(filter);

        System.out.println("Filtered documents in the collection: ");
        for (Document document : filteredCustomers) {
            System.out.println(document.toJson());
        }
    }

    private static void printDocumentsOnce(FindIterable<Document> documents) {
        Set<String> printedDocumentIds = new HashSet<>();
        for (Document document : documents) {
            String documentId = getDocumentId(document);

            if (!printedDocumentIds.contains(documentId)) {
                System.out.println(document.toJson());
                printedDocumentIds.add(documentId);
            }
        }
    }

    private static String getDocumentId(Document document) {
        Object idObject = document.get("_id");
        String documentId;

        if (idObject instanceof Document) {
            Document idDocument = (Document) idObject;
            documentId = idDocument.getString("$oid");
        } else {
            documentId = idObject.toString();
        }

        return documentId;
    }

    private static void handleRedis() {
        try {
            Jedis jedis = new Jedis();

            // Create (Set customer information)
            jedis.hset("customer:1", "name", "John");
            jedis.hset("customer:1", "age", "30");
            jedis.hset("customer:1", "email", "john@example.com");

            // Read (Get customer information)
            String name = jedis.hget("customer:1", "name");
            String age = jedis.hget("customer:1", "age");
            String email = jedis.hget("customer:1", "email");

            System.out.println("Customer Information:");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Email: " + email);

            // Update (Update customer information)
            jedis.hset("customer:1", "age", "31");

            // Read again after update
            age = jedis.hget("customer:1", "age");
            System.out.println("Updated Age: " + age);

            // Delete (Delete customer information)
            jedis.del("customer:1");

            jedis.close();
        } catch (JedisConnectionException e) {
            System.out.println("Could not connect to Redis: " + e.getMessage());
        }
    }

    private static void handleBlockchain() {
        System.out.println("Handling Blockchain...");

        // Create a new blockchain
        Customer.Blockchain blockchain = new Customer.Blockchain();

        //
        Customer customer1 = new Customer(1, "John", "Doe", 30, "john@example.com");
        blockchain.addBlock(customer1, "Transaction 1");

        Customer customer2 = new Customer(2, "Jane", "Smith", 25, "jane@example.com");
        blockchain.addBlock(customer2, "Transaction 2");

        // Print the blockchain
        blockchain.printBlockchain();
    }

    private static String getCustomerInfoFromRedis() {
        try {
            Jedis jedis = new Jedis();

            // Read customer information from Redis
            String name = jedis.hget("customer:1", "name");
            String age = jedis.hget("customer:1", "age");
            String email = jedis.hget("customer:1", "email");

            // Close the connection
            jedis.close();

            // Return customer information as a string
            return "Name: " + name + ", Age: " + age + ", Email: " + email;
        } catch (JedisConnectionException e) {
            System.out.println("Could not connect to Redis: " + e.getMessage());
            return "";
        }
    }
}