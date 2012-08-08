package com.sjsu.webmart.cli;

import static com.sjsu.webmart.util.ConsoleUtil.getOption;
import static com.sjsu.webmart.util.ConsoleUtil.printEnteredOption;
import static com.sjsu.webmart.util.ConsoleUtil.printOptions;

import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

//import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
//import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Bidable;
import com.sjsu.webmart.model.item.Buyable;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.model.item.Rentable;
//import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
//import com.sjsu.webmart.test.ItemData;
import com.sjsu.webmart.util.ConsoleUtil;

public class ItemConsoleHandler {
	protected InventoryServiceImpl itemService;
	private List<ConsoleOption> itemOptions;
	private PrintWriter out;
	private BufferedReader reader;

	public ItemConsoleHandler(PrintWriter out, BufferedReader reader) {
		this.out = out;
		this.reader = reader;
		itemService.getInstance();
		createItemOptions();
	}

	/**
	 * handle main options
	 * 
	 * @throws java.io.IOException
	 */
	public void handleItemOptions() throws IOException {
		Item item = null;
		// ConsoleUtil consoleUtil = new ConsoleUtil();
		InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
		String size = null;
		String weight = null;
		String duration = null;
		String quality = null;
		int itemId;
		AccountService account = AccountServiceImpl.getInstance();
		int type;

		OptionNum secondOption = OptionNum.OPTION_NONE;
		while (true) {
			printOptions(out, secondOption, itemOptions);
			secondOption = getOption(reader);
			switch (secondOption) {
			case OPTION_ONE: {
				System.out.println("Enter Account id :");
				int accId = ConsoleUtil.getIntValue(reader);
				
				System.out
						.println("Do you want to add information for \n 1. Consumer item \n 2. Media item? ");
				type = ConsoleUtil.getIntValue(reader);
				if (type == 1) {
					item = new ConsumerItem(size, weight);
				} else if (type == 2) {
					item = new MediaItem(duration, quality, size);
				}

				else {
					System.out.println("Invalid choice:");
				}
				item.setItemId(isi.lastItemId());

				System.out.println("Enter item Name: ");
				String itemTitle = ConsoleUtil.getInput(reader);

				System.out.println("Enter quantity :");
				int q = ConsoleUtil.getIntValue(reader);

//				System.out.println("Enter Seller Name: ");
//				String sn = ConsoleUtil.getInput(reader);
				
				

				System.out.println("Enter Price");
				float p = ConsoleUtil.getFloatValue(reader);

				System.out.println("Enter Item Description: ");
				String desc = ConsoleUtil.getInput(reader);

				System.out.println("Enter Discount: ");
				float d = ConsoleUtil.getFloatValue(reader);

				if (type == 1) {
					System.out.println("Enter dimension:");
					size = ConsoleUtil.getInput(reader);
					System.out.println("Enter Weight");
					weight = ConsoleUtil.getInput(reader);
					item = new ConsumerItem(size, weight);
					item.setItemTitle(itemTitle);
					item.setQuantity(q);
					item.setSellerName(account.getFirstNameLastName(accId));
					//item.setSellerName(sn);
					item.setPrice(p);
					item.setItemDescription(desc);
					item.setDiscount(d);
				} else if (type == 2) {
					System.out.println("Enter Duration:");
					duration = ConsoleUtil.getInput(reader);
					System.out.println("Enter Quality:");
					quality = ConsoleUtil.getInput(reader);
					System.out.println("Enter Size:");
					size = ConsoleUtil.getInput(reader);
					item = new MediaItem(duration, quality, size);
					item.setItemTitle(itemTitle);
					item.setQuantity(q);
					
					item.setSellerName(account.getFirstNameLastName(accId));
					//item.setSellerName(sn);
					item.setPrice(p);
					item.setItemDescription(desc);
					item.setDiscount(d);
				} else {
					System.out.println("Invalid choice:");
				}

				System.out.println("Enter choice to make this Item");
				System.out.println("1. Rentable");
				System.out.println("2. Buyable");
				System.out.println("3. Biddable");

				int choice = ConsoleUtil.getIntValue(reader);
				if (choice == 1) {
					Rentable rent = new Rentable(item);
					isi.addToRentList(item);
					// isi.addItem(item);
				} else if (choice == 2) {
					Buyable rent = new Buyable(item);
					isi.addToBuyList(item);
					// isi.addItem(item);
				} else if (choice == 3) {
					Bidable rent = new Bidable(item);
					isi.addToBidList(item);
					// isi.addItem(item);
				} else
					System.out.println("Invalid Choice");

			}
				break;
			case OPTION_TWO:
				printEnteredOption(out, itemOptions, secondOption);
				System.out.println("Enter Item Number :");
				itemId = ConsoleUtil.getIntValue(reader);
				// System.out.println("itemId : "+itemId);
				isi.viewItem(itemId);
				break;
			case OPTION_THREE:
				printEnteredOption(out, itemOptions, secondOption);
				System.out.println("Enter Item Number :");
				itemId = ConsoleUtil.getIntValue(reader);
				System.out.println("Enter new information for item " + itemId);
				System.out.println("Enter new Item Title :");
				String title = ConsoleUtil.getInput(reader);
				System.out.println("Enter new Price :");
				float price = ConsoleUtil.getFloatValue(reader);
				System.out.println("Enter new Discount: ");
				float dis = ConsoleUtil.getFloatValue(reader);
				isi.updateItem(itemId, title, price, dis);
				break;
			case OPTION_FOUR:
				printEnteredOption(out, itemOptions, secondOption);
				System.out.println("Enter Item Number :");
				itemId = ConsoleUtil.getIntValue(reader);
				isi.deleteItem(itemId);
				break;
			case OPTION_FIVE:
				printEnteredOption(out, itemOptions, secondOption);
				isi.viewAllItems();
				break;
			case OPTION_EXIT:
				// printEnteredOption(itemOptions, secondOption);
				return;
			default:
				out.println("Invalid Option");
				secondOption = OptionNum.OPTION_NONE;
				break;
			}
		}
	}

	public void createItemOptions() {
		ConsoleOption addItem = new ConsoleOption("Add Item",
				OptionNum.OPTION_ONE, null);
		ConsoleOption viewItem = new ConsoleOption("View Item",
				OptionNum.OPTION_TWO, null);
		ConsoleOption updateItem = new ConsoleOption("Update Item",
				OptionNum.OPTION_THREE, null);
		ConsoleOption deleteItem = new ConsoleOption("Delete Item",
				OptionNum.OPTION_FOUR, null);
		ConsoleOption viewAllItems = new ConsoleOption("View All Items",
				OptionNum.OPTION_FIVE, null);
		itemOptions = new ArrayList<ConsoleOption>();
		itemOptions.add(addItem);
		itemOptions.add(viewItem);
		itemOptions.add(updateItem);
		itemOptions.add(deleteItem);
		itemOptions.add(viewAllItems);
	}
}
