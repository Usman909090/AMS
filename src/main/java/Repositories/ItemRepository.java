package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.Item;
import Utility.DBConnection;
import java.sql.SQLException;

public class ItemRepository {
	
	Connection connection = null;
	
	public static List<Item> getItemsBySeller(int sellerId) {
        List<Item> items = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM items WHERE seller_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, sellerId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double startingPrice = resultSet.getDouble("starting_price");
                String description = resultSet.getString("description");
                items.add(new Item(id, name, startingPrice, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return items;
    }
}
