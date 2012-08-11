package com.sjsu.webmart.model.item;

import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class Bidable extends ItemDecorator{

	
	public Bidable(Item item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

//	public Item itemDetails(Item item){
////		item = isi.viewItem(item.getItemId());
////		return item;
//		return null
//	}
//	
//	public boolean itemAvailable(int itemId){
//		if (isi.getItemStatus(itemId) == true){
//			System.out.println("Item available");
//			return true;
//		}
//		else {
//			System.out.println("Item not Available");
//			return false;
//		}
//		// logic to find item in the inventory
//		
//	}
//	
//	public void bidItem(Item item){
//		Item item1 = isi.viewItem(item.getItemId());
//		// call the methods to invoke bidding and auctioning
//	}
	
}
