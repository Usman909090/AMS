package Models;

public class Item {
    private int id;
    private String name;
    private String description;
    private double startingPrice;

    // Constructors
    public Item(int id, String name, String description, double startingPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    // Getters and Setters
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
    
    public String displayDetails() {
    	return String.format("Item ID: %d\nName: %s\nDescription: %s\nStarting Price: %.2f", id, name, description, startingPrice);
    }
}
