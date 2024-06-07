package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.User;


public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BootDB";
    private static final String USER = "root";
    private static final String PASSWORD = "zait708090";
    
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
            throw e;
        }
        return connection;
    }   
}
