package Models;

public class Item {
    private int id;
    private String name;
    private double startingPrice;
    private String description;
    private int sellerId;

    // Constructor
    public Item(int id, String name, double startingPrice, String description, int sellerId) {
        this.id = id;
        this.name = name;
        this.startingPrice = startingPrice;
        this.description = description;
        this.sellerId = sellerId;
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

	public double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	
    
    

}
   
