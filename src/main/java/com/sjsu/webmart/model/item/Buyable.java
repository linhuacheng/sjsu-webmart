package com.sjsu.webmart.model.item;

import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class Buyable extends ItemDecorator{
	
	public Buyable(Item item) {
		super(item);
		// TODO Auto-generated constructor stub
	}
	
//	public Item itemDetails(Item item){
//		Item item1 = isi.viewItem(item.getItemId());
//		return item1;
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
//	public void buyItem(Item item){
//		Item item1 = isi.viewItem(item.getItemId());
//		
//		// call the function to invoke buying process
//	}
//	
//	public void returnItem(){
//		Item item1 = isi.viewItem(item.getItemId());
//		// call the function to invoke the inventory and add one item there
//	}

}
