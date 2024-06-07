package Models;

import java.util.Date;

public class Bid {
    private int id;
    private Auction auction;
    private User bidder;
    private double amount;
    private Date bidTime;

    // Constructors
    public Bid(int id, Auction auction, User bidder, double amount, Date bidTime) {
        this.id = id;
        this.auction = auction;
        this.bidder = bidder;
        this.amount = amount;
        this.bidTime = bidTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }
}
