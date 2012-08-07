package com.sjsu.webmart.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import com.sjsu.webmart.model.item.Bidable;
import com.sjsu.webmart.model.item.Buyable;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
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
		
		searchkey = Integer.parseInt(getUserInput());
		
		manageSearchKey(searchkey);
		
		searchkey = Integer.parseInt(getUserInput());
		manageSearchKey(searchkey);
		
	}
	
	public static String getUserInput() throws IOException{
		InputStreamReader istream = new InputStreamReader(System.in) ;
        BufferedReader bufRead = new BufferedReader(istream) ;
		String input = bufRead.readLine();
		 
		return input; 
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
		//MediaItem item = new MediaItem(30, "HD", "25MB");
		Item item = null;
		InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
		String size = null;
		String weight = null;
		String duration = null;
		String quality = null;
		int itemId;
		String desc;
		if (searchkey == 1){
			
			System.out.println("Enter Description (ConsumerItem/ MediaItem): ");
//			bufRead = new BufferedReader(new InputStreamReader(System.in));
			desc = getUserInput();;
			if(desc.equalsIgnoreCase("ConsumerItem") || desc.equalsIgnoreCase("Consumer Item")){
				item = new ConsumerItem(size,weight);
			}
			else 
				item = new MediaItem(duration, quality, weight);
			
			item.setItemId(isi.lastItemId()+1);
			
			System.out.println("Enter item Name: ");
			
			String itemTitle = getUserInput();
			
			
			System.out.println("Enter quantity :");
			
			int q = (Integer.parseInt(getUserInput()));
			
			System.out.println("Enter Seller Name: ");
			
			String sn = getUserInput();
			
			System.out.println("Enter Price");
			
			int p = (Integer.parseInt(getUserInput()));
			
			if(desc.equalsIgnoreCase("ConsumerItem") || desc.equalsIgnoreCase("Consumer Item")){
				System.out.println("Enter dimension:");
				
				size =getUserInput();
				
				
				System.out.println("Enter Weight");
				
				weight =getUserInput();
				
				item = new ConsumerItem(size,weight);
				item.setItemTitle(itemTitle);
				item.setQuantity(q);
				item.setSellerName(sn);
				item.setPrice(p);
				
				
			}
			
			else {
				System.out.println("Enter duration:");
				
				duration =getUserInput();
				
				System.out.println("Enter Quality:");
				
				quality =getUserInput();
				
				System.out.println("Enter size:");
				
				size =getUserInput();
				
				item = new MediaItem(duration,quality,size);
				item.setItemTitle(itemTitle);
				item.setQuantity(q);
				item.setSellerName(sn);
				item.setPrice(p);
				

			}
				
			item.setItemDescription(desc);
			
			
			System.out.println("Enter Discount: ");
			
			float d =(Integer.parseInt(getUserInput()));
			item.setDiscount(d);
			
			System.out.println("Enter choice to make this Item");
			System.out.println("1. Rentable");
			System.out.println("2. Buyable");
			System.out.println("3. Biddable");
			
			int choice = (Integer.parseInt(getUserInput()));
			if (choice == 1){
				Rentable rent = new Rentable(item);
				isi.addToRentList(item);
		//		isi.addItem(item);
			}
			else if (choice == 2){
				Buyable rent = new Buyable(item);
				isi.addToBuyList(item);
			//	isi.addItem(item);
			}
			else if (choice == 1){
				Bidable rent = new Bidable(item);
				isi.addToBidList(item);
				//isi.addItem(item);
			}
			else 
				System.out.println("Invalid Choice");
			//item.setItemId(isi.lastItemId());
			//isi.addItem(item);
			isi.viewAllItems();
		}
		
		else if (searchkey == 2){
			System.out.println("Enter Item Number :");
			itemId = Integer.parseInt(getUserInput());
			//System.out.println("itemId : "+itemId);
			isi.viewItem(itemId);
			//System.out.println("Item info:" + isi.);
		}
		else if (searchkey == 3){
			
			System.out.println("Enter Item Number :");
			itemId = Integer.parseInt(getUserInput());
			System.out.println("Enter new information for item "+itemId+"\n");
			System.out.println("Enter new Item Title :");
			String title = getUserInput();
			
			
			System.out.println("Enter new Price :");
			float price = Integer.parseInt(getUserInput());
			
			
			System.out.println("Enter new Discount: ");
			float dis = Integer.parseInt(getUserInput());
			
			
			isi.updateItem(itemId,title,price,dis);
		}
		else if (searchkey == 4){
			System.out.println("Enter Item Number :");
			itemId = Integer.parseInt(getUserInput());
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
