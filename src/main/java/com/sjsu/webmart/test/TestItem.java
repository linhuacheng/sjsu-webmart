package com.sjsu.webmart.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.model.item.Rentable;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class TestItem {
	String search = "";
	
	public static void main(String[] args) throws IOException{
		Integer searchkey;
		
		ItemData id = new ItemData();
		id.initializeInventory();
		
		showItemOptions();
		
		searchkey = getUserInput();
		
		manageSearchKey(searchkey);
		
		searchkey = getUserInput();
		manageSearchKey(searchkey);
		
	}
	
	public static Integer getUserInput() throws IOException{
		InputStreamReader istream = new InputStreamReader(System.in) ;
        BufferedReader bufRead = new BufferedReader(istream) ;
		String input = bufRead.readLine();
		Integer inputInt = Integer.parseInt(input); 
		return inputInt; 
	}
	
	public static void showItemOptions(){
		System.out.println("Select an option:");
		System.out.println("1. Add an Item");
		System.out.println("2. View an Item");
		System.out.println("3. Update an Item");
		System.out.println("4. Delete an Item");
		System.out.println("0. Main Menu              "+"9. Previous Menu");
	}
	
	public static void manageSearchKey(int searchkey) throws IOException{
		MediaItem item = new MediaItem(30, "HD", "25MB");
		
		InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
		
		int itemId;
		if (searchkey == 1){
			item.setItemId(isi.lastItemId()+1);
			
			System.out.println("Enter item Name: ");
			InputStreamReader istream = new InputStreamReader(System.in) ;
	        BufferedReader bufRead = new BufferedReader(istream) ;
			String itemTitle = bufRead.readLine();
			item.setItemTitle(itemTitle);
			
			System.out.println("Enter quantity :");
			bufRead = new BufferedReader(new InputStreamReader(System.in));
			item.setQuantity(Integer.parseInt(bufRead.readLine()));
			
			System.out.println("Enter Seller Name: ");
			bufRead = new BufferedReader(new InputStreamReader(System.in));
			item.setSellerName(bufRead.readLine());
			
			System.out.println("Enter Price");
			bufRead = new BufferedReader(new InputStreamReader(System.in));
			item.setPrice(Integer.parseInt(bufRead.readLine()));
			
			System.out.println("Enter Description: ");
			bufRead = new BufferedReader(new InputStreamReader(System.in));
			item.setItemDescription(bufRead.readLine());
			
			System.out.println("Enter Discount: ");
			bufRead = new BufferedReader(new InputStreamReader(System.in));
			item.setDiscount(Integer.parseInt(bufRead.readLine()));
	
			isi.addItem(item);
			isi.viewAllItems();
		}
		
		else if (searchkey == 2){
			System.out.println("Enter Item Number :");
			itemId = getUserInput();
			//System.out.println("itemId : "+itemId);
			isi.viewItem(itemId);
			//System.out.println("Item info:" + isi.);
		}
		else if (searchkey == 3){
			System.out.println("Enter Item Number :");
			itemId = getUserInput();
			isi.updateItem(itemId);
		}
		else if (searchkey == 4){
			System.out.println("Enter Item Number :");
			itemId = getUserInput();
			isi.deleteItem(itemId);
		}
		else if (searchkey == 0){
			// option to go to main menu
			showItemOptions();
		}
		else if (searchkey == 9){
			// option to go to previous menu
			showItemOptions();
		}
	}
}
