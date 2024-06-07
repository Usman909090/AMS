package Repositories;

import Models.Auction;
import Models.Item;
import Models.User;
import Utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuctionRepository {
    
    public static void addAuctionToDatabase(Auction auction) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBConnection.getConnection();
            String query = "INSERT INTO Auctions (item_id, seller_id, start_price, current_bid, auctioneer_id, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, auction.getItem().getId());
            statement.setInt(2, auction.getSeller().getId());
            statement.setDouble(3, auction.getStartPrice());
            statement.setDouble(4, auction.getCurrentBid());
            statement.setInt(5, auction.getAuctioneerId());
            statement.setTimestamp(6, new java.sql.Timestamp(auction.getStartTime().getTime()));
            statement.setTimestamp(7, new java.sql.Timestamp(auction.getEndTime().getTime()));
            statement.executeUpdate();
            System.out.println("Auction added successfully!");
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
    
    
    
    public static List<Auction> getAuctions() {
        List<Auction> auctions = new ArrayList<>();
        String query = " SELECT a.id AS auction_id, a.start_time, a.end_time, a.start_price, a.current_bid, a.auctioneer_id, i.id AS item_id, i.name AS item_name, i.starting_price, i.description AS item_description, i.seller_id, u.id AS user_id, u.name AS user_name, u.email AS user_email FROM Auctions a JOIN Items i ON a.item_id = i.id JOIN Users u ON a.seller_id = u.id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Item item = new Item(
                    resultSet.getInt("item_id"),
                    resultSet.getString("item_name"),
                    resultSet.getDouble("starting_price"),
                    resultSet.getString("item_description"),
                    resultSet.getInt("seller_id")
                );

                User seller = new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("user_name"),
                    null,
                    resultSet.getString("user_email"),
                    null,  // Assuming no password field to retrieve
                    null   // Assuming role is not needed here or needs different handling
                );

                Auction auction = new Auction(
                    resultSet.getInt("auction_id"),
                    item,
                    seller,
                    resultSet.getTimestamp("start_time"),
                    resultSet.getTimestamp("end_time"),
                    resultSet.getDouble("start_price"),
                    resultSet.getDouble("current_bid"),
                    resultSet.getInt("auctioneer_id")
                );

                auctions.add(auction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Consider throwing an exception or handling it in a way that the caller can respond appropriately.
        }

        return auctions;
    }
}
