import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/School";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "IST888IST888";

    public static void main(String[] args) {
        MySQLDatabase mySQLDatabase = new MySQLDatabase();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a database:");
            System.out.println("1. MySQL");
            System.out.println("2. MongoDB");
            System.out.println("3. Redis");
            System.out.println("4. Blockchain");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleMySQL(mySQLDatabase, scanner);
                    break;
                case 2:
                    handleMongoDB();
                    break;
                case 3:
                    handleRedis();
                    break;
                case 4:
                    handleBlockchain();  // Implement blockchain handling
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    private static void handleMySQL(MySQLDatabase mySQLDatabase, Scanner scanner) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            int mySQLChoice;

            do {
                System.out.println("MySQL operations: ");
                System.out.println("1. Add customer");
                System.out.println("2. Retrieve customers");
                System.out.println("3. Update customer");
                System.out.println("4. Delete customer");
                System.out.println("5. Return to main menu");
                System.out.print("Enter your choice: ");

                mySQLChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (mySQLChoice) {
                    case 1:
                        System.out.print("Enter customer name: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter customer age: ");
                        int customerAge = scanner.nextInt();
                        scanner.nextLine(); //Consume the newline character
                        System.out.print("Enter customer email: ");

                        MySQLDatabase.Customer newCustomer = new MySQLDatabase.Customer(customerName, "Doe", customerAge, customerEmail);
                        mySQLDatabase.insertCustomer(connection, newCustomer);
                        System.out.println("Customer added successfully.");
                        break;
                    case 2:
                        List<MySQLDatabase.Customer> allCustomers = mySQLDatabase.getAllCustomers(connection);
                        System.out.println("All Customers:");
                        allCustomers.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Enter customer name to update: ");
                        String customerFirstNameToUpdate = scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String newFirstName = scanner.nextLine();
                        mySQLDatabase.updateCustomer(connection, customerFirstNameToUpdate, newFirstName, 20, "john@example.com");
                        System.out.println("Customer updated successfully.");
                        break;
                    case 4:
                        System.out.print("Enter customer first name delete: ");
                        String customerFirstNameToDelete = scanner.next();
                        mySQLDatabase.deleteCustomer(connection, customerFirstNameToDelete);
                        System.out.println("Customer deleted successfully.");
                        break;
                    case 5:
                        System.out.println("Returning to the main menu.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } while (mySQLChoice != 5);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void handleMongoDB() {
        MongoDB mongoDb = MongoDB.getInstance();

        Scanner scanner = new Scanner(System.in);
        int mongoChoice;

        do {
            System.out.println("MongoDB Operations:");
            System.out.println("1. Find all customers");
            System.out.println("2. Add a customer");
            System.out.println("3. Update a customer");
            System.out.println("4. Delete a customer");
            System.out.println("5. Clear all customers");
            System.out.println("6. Back to main menu");
            System.out.print("Enter your choice: ");

            mongoChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (mongoChoice) {
                case 1:
                    List<Document> allCustomers = mongoDb.findAllCustomers();
                    allCustomers.forEach(customer -> System.out.println(customer.toJson()));
                    break;
                case 2:
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter customer age: ");
                    int customerAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter customer email: ");
                    String customerEmail = scanner.nextLine();

                    Document newCustomer = new Document("firstName", customerName)
                            .append("lastName", "Doe")
                            .append("age", customerAge)
                            .append("email", customerEmail);

                    mongoDb.insertCustomer(newCustomer);
                    System.out.println("Customer added successfully.");
                    break;
                case 3:
                    System.out.print("Enter customer name to update: ");
                    String customerNameToUpdate = scanner.nextLine();
                    System.out.print("Enter new customer name: ");
                    String newCustomerName = scanner.nextLine();
                    System.out.print("Enter new customer age: ");
                    int newCustomerAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new customer email: ");
                    String newCustomerEmail = scanner.nextLine();

                    Document updatedCustomer = new Document("firstName", newCustomerName)
                            .append("lastName", "Doe")
                            .append("age", newCustomerAge)
                            .append("email", newCustomerEmail);

                    mongoDb.updateCustomer(Filters.eq(("firstName"), customerNameToUpdate),updatedCustomer);
                    System.out.println("Customer updated successfully.");
                    break;
                case 4:
                    System.out.print("Enter customer name to delete: ");
                    String customerNameToDelete = scanner.nextLine();

                    mongoDb.deleteCustomer(Filters.eq("firstName", customerNameToDelete));
                    System.out.println("Customer deleted successfully.");
                    break;
                case 5:
                    mongoDb.clearCustomers();
                    System.out.println("All customers cleared.");
                    break;
                case 6:
                    System.out.println("Back to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice, please choose again.");
            }
        } while (mongoChoice != 6);

        scanner.close();
    }


    private static void handleRedis() {
        // Implement logic for Redis operations
        Redis redis = new Redis("localhost", 6379);

        Scanner scanner = new Scanner(System.in);
        int redisChoice;

        do {
            System.out.println("Redis operations: ");
            System.out.println("1. Add data");
            System.out.println("2. Retrieve data");
            System.out.println("3. Update data");
            System.out.println("4. Delete data");
            System.out.println("5. Return to main menu");
            System.out.print("Enter your choice: ");

            redisChoice = scanner.nextInt();
            scanner.nextLine();

            switch (redisChoice) {
                case 1:
                    System.out.println("Enter key to add:");
                    String key = scanner.nextLine();
                    System.out.println("Enter data to add:");
                    String dataToAdd = scanner.nextLine();
                    redis.addData(key, dataToAdd);  // Add data to Redis
                    System.out.println("Data added successfully.");
                    break;
                case 2:
                    System.out.println("Enter key to retrieve data:");
                    String keyToRetrieve = scanner.nextLine();
                    List<String> retrievedData = redis.retrieveData(keyToRetrieve);

                    System.out.println("Retrieved data: " + retrievedData);
                    for (String data : retrievedData) {
                        System.out.println(data);
                    }
                    break;
                case 3:
                    System.out.println("Enter key to update data:");
                    String keyToUpdate = scanner.nextLine();
                    System.out.println("Enter new data:");
                    String newData = scanner.nextLine();
                    redis.updateData(keyToUpdate, newData);  // Assuming you have a method in your Redis class to update data
                    System.out.println("Data updated successfully.");
                    break;
                case 4:
                    System.out.println("Enter key to delete data:");
                    String keyToDelete = scanner.nextLine();
                    redis.deleteData(keyToDelete);  // Assuming you have a method in your Redis class to delete data
                    System.out.println("Data deleted successfully.");
                    break;
                case 5:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (redisChoice != 5);
    }

    private static void handleBlockchain() {
        Blockchain blockchain = new Blockchain();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Blockchain Operations:");
            System.out.println("1. Add a block");
            System.out.println("2. View blockchain");
            System.out.println("3. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter data for the new block: ");
                    scanner.nextLine(); // consume newline leftover from nextInt()
                    String data = scanner.nextLine();
                    blockchain.addBlock(data);
                    System.out.println("Added a new block with data '" + data + "' to the blockchain.");
                    break;
                case 2:
                    blockchain.printBlockchain();
                    break;
                case 3:
                    System.out.println("Back to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice, please choose again.");
            }
        } while (choice != 3);

        scanner.close();
    }

}