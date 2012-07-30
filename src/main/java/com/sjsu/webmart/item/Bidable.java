package com.sjsu.webmart.item;

public class Bidable extends ItemDecorator{

	
	public Bidable(Item item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	public Item itemDetails(Item item){
		return item;
	}
	
	public boolean itemAvailable(int itemId){
		
		// logic to find item in the inventory
		return true;
	}
	
	public void bidItem(){
		// call the methods to invoke bidding and auctioning
	}
	
}
