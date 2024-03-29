package com.sjsu.webmart.test;

import java.util.ArrayList;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.*;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class ItemData {

	ItemDecorator id;
	InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
	AccountService as = AccountServiceImpl.getInstance();
	
	public void initializeInventory() {
		ConsumerItem citem1 = new ConsumerItem("", "");
		citem1.setItemTitle("Sony Cyber-shot Digital Camera W330");
		//citem1.setSellerName("Priyanka");
		
		citem1.setSellerName(as.getFirstNameLastName(5));
		citem1.setItemId(1);
		citem1.setItemDescription("Refurbished");
		citem1.setPrice(98.9f);
		citem1.setDiscount(0);
		citem1.setQuantity(2);
		citem1.setSize("3 7/8in x 2 1/4in x 11/16in");
		citem1.setWeight("Approx. 4.0oz. (112g)");
		id = new Bidable(citem1);
		isi.addToBidList(citem1);

		ConsumerItem citem2 = new ConsumerItem("", "");
		citem2.setItemTitle("Apple MacBook Pro ");
		//citem2.setSellerName("Priyanka");
		citem2.setSellerName(as.getFirstNameLastName(5));
		citem2.setItemId(2);
		citem2.setItemDescription("15.4in. Laptop - MD103LL/A (Latest Model)");
		citem2.setPrice(1688.00f);
		citem2.setDiscount(0);
		citem2.setQuantity(3);
		citem2.setSize("9.82 in. X 14.35 in. X 15.0 in");
		citem2.setWeight("5.6 lb.");
		id = new Bidable(citem2);
		isi.addToBidList(citem2);

		ConsumerItem citem3 = new ConsumerItem("", "");
		citem3.setItemTitle("Garmin n�vi 1450LTM Automotive GPS Receiver");
//		citem3.setSellerName("Priyanka");
		citem3.setSellerName(as.getFirstNameLastName(5));
		citem3.setItemId(3);
		citem3.setItemDescription("5.0-inch WQVGA TFT touchscreen display");
		citem3.setPrice(151.95f);
		citem3.setDiscount(0);
		citem3.setQuantity(5);
		citem3.setSize("5 in.");
		citem3.setWeight("0.5 lb");
		id = new Buyable(citem3);
		isi.addToBuyList(citem3);

		MediaItem mitem1 = new MediaItem("", "", "");
		mitem1.setItemTitle("Microsoft Office Home & Student 2010");
		//mitem1.setSellerName("Priyanka");
		mitem1.setSellerName(as.getFirstNameLastName(2));
		mitem1.setItemId(4);
		mitem1.setItemDescription("Mac OS X Intel");
		mitem1.setPrice(122.95f);
		mitem1.setDiscount(0);
		mitem1.setQuantity(3);
		mitem1.setSize("7.6 x 5.5 x 1.2 inches");
		mitem1.setDuration("");
		mitem1.setQuality("");
		id = new Buyable(mitem1);
		isi.addToBuyList(mitem1);

		MediaItem mitem2 = new MediaItem("", "", "");
		mitem2.setItemTitle("Mission: Impossible III");
		//mitem2.setSellerName("Priyanka");
		mitem2.setSellerName(as.getFirstNameLastName(2));
		mitem2.setItemId(5);
		mitem2.setItemDescription("Tom Cruise, Philip Seymour Hoffman");
		mitem2.setPrice(1.0f);
		mitem2.setDiscount(0);
		mitem2.setQuantity(1);
		mitem2.setSize("");
		mitem2.setDuration("2 hours 6 minutes");
		mitem2.setQuality("blu-ray");
		id = new Rentable(mitem2);
		isi.addToRentList(mitem2);

		MediaItem mitem3 = new MediaItem("", "", "");
		mitem3.setItemTitle("The Dark Knight");
//		mitem3.setSellerName("Priyanka");
		mitem3.setSellerName(as.getFirstNameLastName(2));
		mitem3.setItemId(2);
		mitem3.setItemDescription("Christian Bale, Michael Caine");
		mitem3.setPrice(2.99f);
		mitem3.setDiscount(0);
		mitem3.setQuantity(3);
		mitem3.setSize("");
		mitem3.setDuration("2 hours 33 minutes");
		mitem3.setQuality("HD");
		id = new Rentable(mitem3);
		isi.addToRentList(mitem3);

//        isi.createNewConsumerItem(ItemType.BIDABLE,"Kindle Fire", 200, "Amazon Kindle Fire Tablet", ".5lbs", 10);
//        isi.createNewConsumerItem(ItemType.BIDABLE,"Sony NEX5N", 699, "Sony Compact Interchangeable Lens Touchscreen Camera\n" +
//                "With 18-55mm lens" , "3lbs", 2);
	}

}