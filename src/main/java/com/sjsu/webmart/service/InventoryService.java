package com.sjsu.webmart.service;

import com.sjsu.webmart.model.item.Item;

public class InventoryService {

	private static InventoryService instance = null;

	private InventoryService() {
	}

	public static InventoryService getInstance() {
		if (instance == null) {
			synchronized (InventoryService.class) {
				if (instance == null) {
					instance = new InventoryService();
				}
			}
		}
		return instance;
	}
	
	public void addItem(Item item){
		
	}
	
	public void deleteItem(Item item){
		
	}
	
	public void viewItem(Item item){
		
	}
	
	public void updateItem(Item item){
		
	}
	
	public boolean getItemStatus(Item item){
		return true; // or false depending upon availablity
	}
}
