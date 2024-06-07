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
                items.add(new Item(id, name, startingPrice, description, sellerId));
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
	
	
	
	public static void addItemToDatabase(Item item) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            String query = "INSERT INTO items (name, description, starting_price, seller_id) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getStartingPrice());
            statement.setInt(4, item.getSellerId());
            statement.executeUpdate();
            System.out.println("Item added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	
	public static void removeItemFromDatabase(int itemId) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = DBConnection.getConnection();
	        String query = "DELETE FROM items WHERE id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, itemId);
	        int affectedRows = statement.executeUpdate();
	        if (affectedRows > 0) {
	            System.out.println("Item removed successfully!");
	        } else {
	            System.out.println("No item was found with ID: " + itemId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error removing item: " + e.getMessage());
	    } finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	
	public static Item findItemById(int itemId) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DBConnection.getConnection();
	        String query = "SELECT * FROM items WHERE id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, itemId);
	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            return new Item(
	                resultSet.getInt("id"),
	                resultSet.getString("name"),
	                resultSet.getDouble("starting_price"),
	                resultSet.getString("description"),
	                resultSet.getInt("seller_id")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return null;
	}
	
	
	
	public static void updateItemInDatabase(Item item) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = DBConnection.getConnection();
	        String query = "UPDATE items SET name = ?, description = ?, starting_price = ? WHERE id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, item.getName());
	        statement.setString(2, item.getDescription());
	        statement.setDouble(3, item.getStartingPrice());
	        statement.setInt(4, item.getId());
	        int affectedRows = statement.executeUpdate();
	        if (affectedRows > 0) {
	            System.out.println("Item updated successfully!");
	        } else {
	            System.out.println("Error updating item.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	
	public static List<Item> getAvailableItemsForAuction() {
        List<Item> items = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            // This SQL query selects items that are not currently linked to any active auction.
            String query = "SELECT * FROM Items i WHERE NOT EXISTS (SELECT 1 FROM Auctions a WHERE a.item_id = i.id)";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double startingPrice = resultSet.getDouble("starting_price");
                String description = resultSet.getString("description");
                int sellerId = resultSet.getInt("seller_id");  // Assuming your items table has a seller_id
                items.add(new Item(id, name, startingPrice, description, sellerId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return items;
    }



	
}
