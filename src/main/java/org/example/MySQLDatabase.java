package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MySQLDatabase {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/School";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "IST888IST888";

    public static void main(String[] args) {
        MySQLDatabase mySQLDatabase = new MySQLDatabase();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an operation:");
            System.out.println("1. Manage Customers with MySQL");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleMySQL(mySQLDatabase);
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void handleMySQL(MySQLDatabase mySQLDatabase) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Example operation: Read all customers and print
            mySQLDatabase.getAllCustomers(connection).forEach(System.out::println);
            // You can add more operations here such as insert, update, delete based on further user inputs
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a customer
    public void insertCustomer(Connection connection, Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (firstName, lastName, age, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    // Get all customers from the database
    public List<Customer> getAllCustomers(Connection connection) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT firstName, lastName, age, email FROM customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                customers.add(new Customer(firstName, lastName, age, email));
            }
        }
        return customers;
    }

    // Update a customer
    public void updateCustomer(Connection connection, String oldFirstName, String newFirstName, int newAge, String newEmail) throws SQLException {
        String sql = "UPDATE customers SET firstName = ?, age = ?, email = ? WHERE firstName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, newAge);
            preparedStatement.setString(3, newEmail);
            preparedStatement.setString(4, oldFirstName);
            preparedStatement.executeUpdate();
        }
    }

    // Delete a customer
    public void deleteCustomer(Connection connection, String firstName) throws SQLException {
        String sql = "DELETE FROM customers WHERE firstName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();
        }
    }
}
