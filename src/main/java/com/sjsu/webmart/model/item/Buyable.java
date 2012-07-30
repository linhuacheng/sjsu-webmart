package com.sjsu.webmart.model.item;

public class Buyable extends ItemDecorator{

	public Buyable(Item item) {
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
	
	public void buyItem(){
		
		// call the function to invoke buying process
	}
	
	public void returnItem(){
		// call the function to invoke the inventory and add one item there
	}

}
