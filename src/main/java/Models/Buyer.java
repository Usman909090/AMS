package Models;

import java.util.List;

import Interfaces.UserItems;

public class Buyer extends User implements UserItems {
    private int numberOfBids;
    private UserInventory inventory;

    public Buyer(int id, String name, String cnic, String email, String password, double balance, int numberOfBids) {
        super(id, name, cnic, email, password, balance, UserRole.AUCTIONEER);
        this.numberOfBids = numberOfBids;
        this.inventory = new UserInventory(id);
    }

    // Getters and Setters
    public int getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
    }
    
    @Override
    public void addItem(Item i) {
    	inventory.getItems().add(i);
    }
    
    @Override
    public void removeItem(int index) {
        inventory.getItems().remove(index);
    }

    @Override
    public Item getItem(int index) {
        return inventory.getItems().get(index);
    }
    
    @Override
    public String displayItemDetails(int index) {
    	return getItem(index).displayDetails();
    }

	@Override
	public List<Item> getItems() {
		return inventory.getItems();
	}
}
