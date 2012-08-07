package com.sjsu.webmart.service;

import java.util.List;

import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;

public interface InventoryService {

	// public void addItem(Item item);

	public void deleteItem(int itemId);

	public Item getItem(int itemId);

	public Item viewItem(int itemId);

	public boolean getItemStatus(int itemId);

	public void updateItem(int itemId, String title, float price, float discount);

	public void updateQuantity(int itemId, int quantity);

	public List<Item> listItem(ItemType type);

	Item createNewConsumerItem(ItemType itemType, String title, float price,
			String description, String weight, int quantity);
}
