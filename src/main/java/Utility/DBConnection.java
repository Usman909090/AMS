package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.User;
import Models.UserRole;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ams?autoReconnect=true&useSSL=false";
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
    
    public static int getLatestUserID() throws SQLException {
		Connection connection = null;
        PreparedStatement statement = null;

        int id = -1;
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT MAX(id) as latest_id FROM Users";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            	
            if (rs.next()) {
            	id = rs.getInt("latest_id");
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        
        return id;
	}
    
	public static User getUserById(Integer id) throws SQLException {
		Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("cnic"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    UserRole.getRole(resultSet.getString("role"))
                );
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return user;
	}
}
