package com.sjsu.webmart.model.item;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.RentOrder;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class Rentable extends ItemDecorator{

	InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
	//private List<Item> items = new ArrayList<Item>();
	
	public Rentable(Item item) {
		
		super(item);
		// TODO Auto-generated constructor stub
	}

	public Item itemDetails(Item item){
		Item item1 = isi.viewItem(item.getItemId());
		return item1;
	}
	
	public boolean itemAvailable(int itemId){
		if (isi.getItemStatus(itemId) == true){
			System.out.println("Item available");
			return true;
		}
		else {
			System.out.println("Item not Available");
			return false;
		}
		// logic to find item in the inventory
		
	}
	
	public void rentItem(Item item){
		Item item1 = isi.viewItem(item.getItemId());
		//call the order process 
//		item.setItemId(itemId);
//		Order rentOrder = new RentOrder(itemId);
//		rentOrder.processOrder();
	}
	
	public void returnItem(){
		Item item1 = isi.viewItem(item.getItemId());
		// call the process to return the item to inventory
	}
}
