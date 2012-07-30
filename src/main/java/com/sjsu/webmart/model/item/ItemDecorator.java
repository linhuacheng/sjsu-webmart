package com.sjsu.webmart.model.item;

public class ItemDecorator extends Item{

	protected Item item;
	
	public ItemDecorator(Item item){
		this.item = item;
	}
	
	
}
