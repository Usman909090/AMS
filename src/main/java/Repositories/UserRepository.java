package Repositories;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.common.hash.Hashing;
import com.mysql.cj.protocol.a.authentication.Sha256PasswordPlugin;

import Controllers.SessionController;
import Interfaces.GenericCRUD;
import Utility.DBConnection;
import Models.User;
import Models.UserRole;


public class UserRepository implements GenericCRUD<User, Integer> {
	
	@Override
    public <S extends User> S save(S user) throws SQLException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            
            // Check if the user with the given email already exists
            String selectQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, user.getEmail());
            resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            
            if (count > 0) {
                // User with the same email already exists
                throw new SQLException("User with this email already exists.");
            }
            
            // Insert the new user
            String insertQuery = "INSERT INTO users (name, cnic, role, email, password) VALUES (?, ?, ?, ?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, user.getName());
            insertStatement.setString(2, user.getCnic());
            insertStatement.setString(3, user.getRole());
            insertStatement.setString(4, user.getEmail());
            
            
            insertStatement.setString(5, user.getPassword());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert user: " + e.getMessage());
            throw e;
        } finally {
            // Close resources
            if (resultSet != null) {
                resultSet.close();
            }
            if (selectStatement != null) {
                selectStatement.close();
            }
            if (insertStatement != null) {
                insertStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return user;
    }
    
    public static User authenticateUser(String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
           System.out.println(email);
           System.out.println(password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cnic = resultSet.getString("cnic");
                UserRole role = null;
                if(resultSet.getString("role").equals("SELLER")) {
                	role = UserRole.SELLER;
                }
                if(resultSet.getString("role").equals("BUYER")) {
                	role = UserRole.BUYER;
                }
                String userEmail = resultSet.getString("email");
                String userPassword = resultSet.getString("password");
                System.out.println(password);
                user = new User(id, name, cnic, userEmail, userPassword, role);
                
                
                
                
                
            }
        } finally {
            // Close resources in reverse order of their creation
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            
        }
        return user;
    }

	@Override
	public User findById(Integer id) throws SQLException {
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

	@Override
	public List findAll() throws SQLException {
		Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM users";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("cnic"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    UserRole.getRole(resultSet.getString("role"))
                );
                users.add(user);
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return users;
	}

	@Override
	public void update(User user) throws SQLException {
		Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            String query = "UPDATE users SET name = ?, cnic = ?, role = ?, email = ?, password = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getCnic());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());
            String hashedPassword = Hashing.sha256().hashString("ams" + user.getPassword(), StandardCharsets.UTF_8).toString();
            statement.setString(5, hashedPassword);
            statement.setInt(6, user.getId());
            statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            String query = "DELETE FROM users WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
	
	
}
