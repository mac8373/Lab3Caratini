/**
 * Project: Lab3Caratini
 * Purpose Details: This project demonstrates CRUD for MySQL database.
 * Course: IST242
 * Author: Maximo Caratini
 * Date Developed: 2024-02-15
 * Last Date Changed: 2024-02-29
 * Rev: 1.0
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabase {
    // JDBC connection info
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/Store";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "IST888IST888";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create
            insertCustomer(connection, 1, "John", "Doe", 20, "john@example.com");

            // Read
            List<Customer> customers = getAllCustomers(connection);
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            // Update
            updatecustomer(connection, 1, "Updated First Name");

            // Read again
            customers = getAllCustomers(connection);
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            // Delete
            deleteCustomer(connection, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Insert customer into the database
    private static void insertCustomer(Connection connection, int id, String firstName, String lastName, int age, String email) throws SQLException {
        String sql = "INSERT INTO customers (id, firstName, lastName, age, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
        }
    }

    private static List<Customer> getAllCustomers(Connection connection) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, firstName, lastName, age, email FROM customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                customers.add(new Customer(id, firstName, lastName, age, email));
            }
        }
        return customers;
    }

    private static void updatecustomer(Connection connection, int id, String newFirstName) throws SQLException {
        String sql = "UPDATE customers SET firstName = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    private static void deleteCustomer(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}