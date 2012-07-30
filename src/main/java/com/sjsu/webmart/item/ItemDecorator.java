package com.sjsu.webmart.item;

public class ItemDecorator extends Item{

	protected Item item;
	
	public ItemDecorator(Item item){
		this.item = item;
	}
	
	
}
