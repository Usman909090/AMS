package Models;

import java.util.Date;

public class Auction {
    private int id;
    private Item item;
    private User seller;
    private Date startTime;
    private Date endTime;
    private double startPrice;
    private double currentBid;
    private int auctioneerId;

    // Constructors
    public Auction(int id, Item item, User seller, Date startTime, Date endTime, double startPrice, double currentBid, int auctioneerId) {
        this.id = id;
        this.item = item;
        this.seller = seller;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.currentBid = currentBid;
        this.auctioneerId = auctioneerId;
    }

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

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	public double getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(double currentBid) {
		this.currentBid = currentBid;
	}

	public int getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(int auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

    
}
