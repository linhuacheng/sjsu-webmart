package com.sjsu.webmart.service;

import com.sjsu.webmart.model.item.Item;

public interface InventoryService {

	public void addItem(Item item);
	
	public void deleteItem(int itemId);
	
	public Item viewItem(int itemId);
	
	public void updateItem(int intemId);
	
	public boolean getItemStatus(int itemId); 
}
