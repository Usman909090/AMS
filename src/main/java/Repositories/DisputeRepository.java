package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.GenericCRUD;
import Utility.DBConnection;
import Models.Dispute;
import Models.User;

public class DisputeRepository implements GenericCRUD<Dispute, Integer> {

	@Override
	public <S extends Dispute> S save(S dispute) throws SQLException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            
            // Check if the user with the given email already exists
            String selectQuery = "SELECT COUNT(*) FROM disputes WHERE id = ?";
            selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, dispute.getId());
            resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            
            if (count > 0) {
                // User with the same email already exists
                throw new SQLException("User with this email already exists.");
            }
            
            // Insert the new user
            String insertQuery = "INSERT INTO disputes (reporter_id, description, status) VALUES (?, ?, ?)";
    		insertStatement = connection.prepareStatement(insertQuery);
    		insertStatement.setInt(1, dispute.getReporter().getId());
    		insertStatement.setString(2, dispute.getDescription());
    		insertStatement.setString(3, dispute.getStatus());
    		
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
        
        return dispute;
    }

    public void addAffectedUser(int disputeId, int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStatement = null;

        try {
            connection = DBConnection.getConnection();
            String insertQuery = "INSERT INTO DisputeAffectedUsers (dispute_id, user_id) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, disputeId);
            insertStatement.setInt(2, userId);
            insertStatement.executeUpdate();
        } finally {
            if (insertStatement != null) insertStatement.close();
            if (connection != null) connection.close();
        }
    }

	@Override
	public Dispute findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public List<Dispute> findAll() throws SQLException {
		List<Dispute> disputes = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection()) {
			String query = "SELECT d.id, d.reporter_id, d.description, d.status, GROUP_CONCAT(u.id SEPARATOR ', ') AS affected_users "
                + "FROM disputes d "
                + "INNER JOIN DisputeAffectedUsers du ON d.id = du.dispute_id "
                + "INNER JOIN Users u ON du.user_id = u.id "
                + "GROUP BY d.id";

			try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
		           int id = resultSet.getInt("id");
		           int reporterId = resultSet.getInt("reporter_id");
		           String description = resultSet.getString("description");
		           String status = resultSet.getString("status");
		           String affectedUsersString = resultSet.getString("affected_users");
		           
		           List<User> affectedUsers = new ArrayList<>();
		           if (affectedUsersString != null && !affectedUsersString.isEmpty()) {
		               String[] a_ids = affectedUsersString.split(", ");
		               for (String a_id : a_ids) {
		                   affectedUsers.add(DBConnection.getUserById(Integer.parseInt(a_id.trim())));
		               }
		           }
		
		           // Retrieve reporter details
		           User reporter = DBConnection.getUserById(reporterId);
		
		           // Create Dispute object and add to disputeData list
		           Dispute dispute = new Dispute(id, reporter, description, status, affectedUsers);
		           disputes.add(dispute);
				}
			}
		}
		return disputes;
	}

	@Override
	public void update(Dispute dispute) throws SQLException {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            String updateQuery = "UPDATE Disputes SET status = ? WHERE id = ?";
            insertStatement = connection.prepareStatement(updateQuery);
            insertStatement.setString(1, dispute.getStatus());
            insertStatement.setInt(2, dispute.getId());
            insertStatement.executeUpdate();
        } finally {
            if (insertStatement != null) insertStatement.close();
            if (connection != null) connection.close();
        }
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		
	}
}
