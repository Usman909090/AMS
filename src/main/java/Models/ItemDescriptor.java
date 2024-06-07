package Models;

import java.util.ArrayList;
import java.util.List;

public class ItemDescriptor {
    private int id;
    private String name;
    private String description;
    private double startingPrice;

    private static List<ItemDescriptor> lostItems = new ArrayList<>();

    public ItemDescriptor(int id, String name, String description, double startingPrice) {
	    this.id = id;
	    this.name = name;
	    this.description = description;
	    this.startingPrice = startingPrice;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public static void addLostItem(ItemDescriptor ItemDescriptor) {
        lostItems.add(ItemDescriptor);
    }

    public static List<ItemDescriptor> getLostItems() {
        return lostItems;
    }
}
