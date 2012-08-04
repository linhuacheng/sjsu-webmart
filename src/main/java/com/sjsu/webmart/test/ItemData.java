package com.sjsu.webmart.test;

import java.util.ArrayList;

import com.sjsu.webmart.model.item.Bidable;
import com.sjsu.webmart.model.item.Buyable;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemDecorator;
import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.model.item.Rentable;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class ItemData {

	
	String cItem[] = {"Bicycle", "SkateBoard", "TV", "DVDPlayer", "Laptop"}; 
	String mItem[] ={"Age of Empires", "Harry Potter", "SlumDog Millionaire", "Prince of Persia", ""};
	String seller[]= {"Priyanka","Nikitha"};
	ItemDecorator id;
	InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
	
	public void initializeInventory(){
		
		for (int i = 0; i < 4; i++){
			MediaItem mitem =new MediaItem("30","HD","250MB");
			mitem.setItemId(isi.lastItemId()+1);
			mitem.setItemTitle(mItem[i]);
			mitem.setSellerName(seller[0]);
			mitem.setItemDescription("MediaItem");
			mitem.setDiscount(0);
			id = decorateItem(mitem);
			//isi.addItem(id);
			
		}
		for (int i = 0; i < 4; i++){
			ConsumerItem citem =new ConsumerItem("1X1","1kg" );
			//citem.setItemId(InventoryServiceImpl.id);
			citem.setItemId(isi.lastItemId()+1);
			citem.setItemTitle(cItem[i]);
			citem.setSellerName(seller[1]);
			citem.setItemDescription("ConsumerItem");
			citem.setDiscount(0);
			id = decorateItem(citem);
			
			//isi.addItem(id);
		}
		isi.viewAllItems();
	}
	private ItemDecorator decorateItem(Item mitem) {
		// TODO Auto-generated method stub
		if((mitem.getItemTitle() == cItem[0]) || (mitem.getItemTitle() == cItem[1]) || (mitem.getItemTitle() == mItem[0]) || (mitem.getItemTitle() == mItem[1])){
			id = new Rentable(mitem);
			isi.addToRentList(mitem);
		}
		else if((mitem.getItemTitle() == cItem[2]) || (mitem.getItemTitle() == mItem[2])){
			id = new Bidable(mitem);
			isi.addToBidList(mitem);
		}
		else{ 
			id = new Buyable(mitem);
			isi.addToBuyList(mitem);
		}
		return id;
	}
}