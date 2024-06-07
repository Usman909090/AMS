package Models;

import java.util.ArrayList;
import java.util.List;

public class UserInventory {
    private int userId;
    private List<Item> items;

    // Constructors
    public UserInventory(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public void setId(int id) {
        this.userId = id;
    }

    public List<Item> getItems() {
        return items;
    }

    // Methods to add and remove items from the inventory
    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }
}
