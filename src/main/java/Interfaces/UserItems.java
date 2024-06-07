package Interfaces;

import java.util.List;

import Models.Item;

public interface UserItems {
	void addItem(Item i);
	void removeItem(int index);
	String getItemDescription(int index);
	Item getItem(int index);
	List<Item> getItems();
}
