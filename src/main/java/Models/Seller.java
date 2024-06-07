package Models;

import java.util.ArrayList;
import java.util.List;

import Interfaces.UserItems;

public class Seller extends User implements UserItems {
    private int numberOfAuctions;
    private UserInventory inventory;
    
    public Seller(int id, String name, String cnic, String email, String password) {
    	super(id, name, cnic, email, password, UserRole.SELLER);
        this.numberOfAuctions = 0;
        this.inventory = new UserInventory(id);
    }

    // Getters and Setters
    public int getNumberOfAuctions() {
        return numberOfAuctions;
    }

    public void setNumberOfAuctions(int numberOfAuctions) {
        this.numberOfAuctions = numberOfAuctions;
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
    public String getItemDescription(int index) {
    	return getItem(index).getDescription();
    }

	@Override
	public List<Item> getItems() {
		return inventory.getItems();
	}
}
