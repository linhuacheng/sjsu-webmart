package com.sjsu.webmart.cli;

import static com.sjsu.webmart.util.ConsoleUtil.getOption;
import static com.sjsu.webmart.util.ConsoleUtil.printEnteredOption;
import static com.sjsu.webmart.util.ConsoleUtil.printOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Bidable;
import com.sjsu.webmart.model.item.Buyable;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.model.item.Rentable;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.test.ItemData;

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
		InventoryServiceImpl isi = InventoryServiceImpl.getInstance();
		String size = null;
		String weight = null;
		String duration = null;
		String quality = null;
		int itemId;
		String desc;
		ItemData id = new ItemData();
		id.initializeInventory();

		OptionNum secondOption = OptionNum.OPTION_NONE;
		while (true) {
			printOptions(out, secondOption, itemOptions);
			secondOption = getOption(reader);
			switch (secondOption) {
			case OPTION_ONE: {
				System.out
						.println("Enter Description (ConsumerItem/ MediaItem): ");
				// bufRead = new BufferedReader(new
				// InputStreamReader(System.in));
				desc = getUserInput();
				;
				if (desc.equalsIgnoreCase("ConsumerItem")
						|| desc.equalsIgnoreCase("Consumer Item")) {
					item = new ConsumerItem(size, weight);
				} else
					item = new MediaItem(duration, quality, weight);

				item.setItemId(isi.lastItemId() + 1);

				System.out.println("Enter item Name: ");

				String itemTitle = getUserInput();

				System.out.println("Enter quantity :");

				int q = (Integer.parseInt(getUserInput()));

				System.out.println("Enter Seller Name: ");

				String sn = getUserInput();

				System.out.println("Enter Price");

				int p = (Integer.parseInt(getUserInput()));

				if (desc.equalsIgnoreCase("ConsumerItem")
						|| desc.equalsIgnoreCase("Consumer Item")) {
					System.out.println("Enter dimension:");

					size = getUserInput();

					System.out.println("Enter Weight");

					weight = getUserInput();

					item = new ConsumerItem(size, weight);
					item.setItemTitle(itemTitle);
					item.setQuantity(q);
					item.setSellerName(sn);
					item.setPrice(p);

				}

				else {
					System.out.println("Enter duration:");

					duration = getUserInput();

					System.out.println("Enter Quality:");

					quality = getUserInput();

					System.out.println("Enter size:");

					size = getUserInput();

					item = new MediaItem(duration, quality, size);
					item.setItemTitle(itemTitle);
					item.setQuantity(q);
					item.setSellerName(sn);
					item.setPrice(p);

				}

				item.setItemDescription(desc);

				System.out.println("Enter Discount: ");

				float d = (Integer.parseInt(getUserInput()));
				item.setDiscount(d);

				System.out.println("Enter choice to make this Item");
				System.out.println("1. Rentable");
				System.out.println("2. Buyable");
				System.out.println("3. Biddable");

				int choice = (Integer.parseInt(getUserInput()));
				if (choice == 1) {
					Rentable rent = new Rentable(item);
					isi.addToRentList(item);
					isi.addItem(item);
				}
				else if (choice == 2){
					Buyable rent = new Buyable(item);
					isi.addToBuyList(item);
					isi.addItem(item);
				}
				else if (choice == 1){
					Bidable rent = new Bidable(item);
					isi.addToBidList(item);
					isi.addItem(item);
				}
				else 
					System.out.println("Invalid Choice");
			}
				break;
			case OPTION_TWO:
				printEnteredOption(out, itemOptions, secondOption);
				System.out.println("Enter Item Number :");
				itemId = Integer.parseInt(getUserInput());
				// System.out.println("itemId : "+itemId);
				isi.viewItem(itemId);
				break;
			case OPTION_THREE:
				printEnteredOption(out, itemOptions, secondOption);
				System.out.println("Enter Item Number :");
				itemId = Integer.parseInt(getUserInput());
				System.out.println("Enter new information for item " + itemId);
				System.out.println("Enter new Item Title :");
				String title = getUserInput();
				System.out.println("Enter new Price :");
				float price = Integer.parseInt(getUserInput());
				System.out.println("Enter new Discount: ");
				float dis = Integer.parseInt(getUserInput());
				isi.updateItem(itemId, title, price, dis);
				break;
			case OPTION_FOUR:
				printEnteredOption(out, itemOptions, secondOption);
				System.out.println("Enter Item Number :");
				itemId = Integer.parseInt(getUserInput());
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

	public static String getUserInput() throws IOException {
		InputStreamReader istream = new InputStreamReader(System.in);
		System.out.println("\n");
		BufferedReader bufRead = new BufferedReader(istream);
		String input = bufRead.readLine();
		return input;
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
