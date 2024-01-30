package com.inventory.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inventory.Dao.Product;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inv";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    public static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public void executeQuery(String query) {
        // Implement query execution logic
    }

    public Object retrieveData(String query) {
        // Implement data retrieval logic
        return null;
    }
    public static boolean addProduct(Product product) {
        try (Connection connection = connectToDatabase()) {
            String query = "INSERT INTO Products (ProductName, Quantity) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setInt(2, product.getQuantity());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
