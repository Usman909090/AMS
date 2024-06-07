package Models;

import java.util.Date;

public class Auction {
    private int id;
    private Item item;
    private User seller;
    private Date startTime;
    private Date endTime;

    // Constructors
    public Auction(int id, Item item, User seller, Date startTime, Date endTime) {
        this.id = id;
        this.item = item;
        this.seller = seller;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
