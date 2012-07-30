package com.sjsu.webmart.model.item;

public class Rentable extends ItemDecorator{

	public Rentable(Item item) {
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
	
	public void rentItem(){
		//call the order process 
	}
	
	public void returnItem(){
		// call the process to return the item to inventory
	}
}
