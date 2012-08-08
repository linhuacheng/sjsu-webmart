package com.sjsu.webmart.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.item.Bidable;
import com.sjsu.webmart.model.item.Buyable;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.model.item.Rentable;
import com.sjsu.webmart.model.order.FulfillmentType;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.service.InventoryService;

public class InventoryServiceImpl implements InventoryService {

	private static InventoryServiceImpl instance = null;
	private static int id = 1;
	private static List<Item> rentitems = new ArrayList<Item>();
	private static List<Item> biditems = new ArrayList<Item>();
	private static List<Item> buyitems = new ArrayList<Item>();

	public static InventoryServiceImpl getInstance() {
		if (instance == null) {
			synchronized (InventoryServiceImpl.class) {
				if (instance == null) {
					instance = new InventoryServiceImpl();
				}
			}
		}
		return instance;
	}
	
//	
	public List<FulfillmentType> getShippingOptions(Item i) {
		List<FulfillmentType> fulfill = new ArrayList<FulfillmentType>();
		if (i.getItemDescription() == "ConsumerItem"
				|| i.getItemDescription() == "Consumer Item") {
			fulfill.add(FulfillmentType.COURIER);
			fulfill.add(FulfillmentType.STORE);

		} else {
			fulfill.add(FulfillmentType.COURIER);
			fulfill.add(FulfillmentType.ONLINE);
			fulfill.add(FulfillmentType.STORE);
		}
		return fulfill;
	}

	/*
	 * public List<Item> listItem() { return items; }
	 */

	public int lastItemId() {
		int count = 0;
		List<Item> itemtemp = new ArrayList<Item>();
		itemtemp.addAll(rentitems);
		itemtemp.addAll(biditems);
		itemtemp.addAll(buyitems);
		for (Item i : itemtemp) {
			count++;
		}
		return count;
	}

	// public void addItem(Item item) {
	// // TODO Auto-generated method stub
	// items.add(item);
	// System.out.println("Item no. " + item.getItemId() + " is added");
	// }

	@Override
	public Item createNewConsumerItem(ItemType itemType, String title,
			float price, String description, String weight, int quantity) {
		ConsumerItem consumerItem = new ConsumerItem();
		// consumerItem.setItemId(id++);
		consumerItem.setItemDescription(description);
		consumerItem.setItemTitle(title);
		consumerItem.setPrice(price);
		consumerItem.setQuantity(quantity);
		consumerItem.setWeight(weight);

		if (ItemType.BIDABLE.equals(itemType)) {
			Item item = new Bidable(consumerItem);
			addToBidList(item);
		} else if (ItemType.RENTABLE.equals(itemType)) {
			Item item = new Rentable(consumerItem);
			addToRentList(item);
		} else {
			Item item = new Buyable(consumerItem);
			addToBuyList(item);
		}
		return consumerItem;
	}

	public void viewAllItems() {
		// TODO Auto-generated method stub
		List<Item> itemtemp = new ArrayList<Item>();
		itemtemp.addAll(rentitems);
		itemtemp.addAll(biditems);
		itemtemp.addAll(buyitems);
				
		String format = "|%1$-10s|%2$-50s|%3$-10s|%4$-15s|%5$-10s|%6$-10s|%7$-50s|%8$-10s|%9$-30s|%10$-30s| \n";
		System.out.println("ALL CONSUMER ITEMS");
		System.out
		.println("___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			System.out.format(format, "ITEM ID", "TITLE", "QUANTITY", "SELLER NAME","RATING" , "PRICE", "DESCRIPTION", "DISC.","SIZE","WEIGHT");
		for (Item i: itemtemp){
			
			if (i instanceof ConsumerItem){
				
				System.out
						.println("___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
				ConsumerItem it = (ConsumerItem)i;
				System.out.format(format, i.getItemId(),i.getItemTitle(), i.getQuantity(), i.getSellerName(),i.getRating(), i.getPrice(), i.getItemDescription(), i.getDiscount(), it.getSize(), it.getWeight());
				System.out
						.println("___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			}
		}
		
		System.out.println("\n ALL MEDIA ITEMS");
		String format1 = "|%1$-10s|%2$-50s|%3$-10s|%4$-15s|%5$-10s|%6$-10s|%7$-50s|%8$-10s|%9$-30s|%10$-30s|%11$-10s|\n";
		System.out
				.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		System.out.format(format1, "ITEM ID", "TITLE", "QUANTITY", "SELLER NAME","RATING" , "PRICE", "DESCRIPTION", "DISC.","SIZE","DURATION","QUALITY");
		for(Item i:itemtemp){
			if (i instanceof MediaItem){
								System.out
						.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
				MediaItem it = (MediaItem)i;
				System.out.format(format1, i.getItemId(),i.getItemTitle(), i.getQuantity(), i.getSellerName(),i.getRating(), i.getPrice(), i.getItemDescription(), i.getDiscount(), it.getSize(), it.getDuration(), ((MediaItem) i).getQuality());
				System.out
						.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			}
		}
//		String format = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
//		System.out
//				.println("_________________________________________________________________");
//		System.out.format(format, "ITEM ID", "TITLE", "QUANTITY", "SELLER NAME", "PRICE");
//		System.out
//				.println("_________________________________________________________________");
//		for (Order order : orderService.findOrders(filter)) {
//			System.out.format(format, order.getOrderId(), order.getOrderType(),
//					order.getItem().getItemDescription(), order.getCost());
//		}
//		System.out
//				.println("_________________________________________________________________");
//		for (Item i : itemtemp) {
//			System.out
//					.println("************************************************************");
//			System.out.println("Item Id :" + i.getItemId());
//			System.out.println("Item Title: " + i.getItemTitle());
//			System.out.println("Number of Items Available: " + i.getQuantity());
//			System.out.println("Seller Name: " + i.getSellerName());
//			System.out.println("Item Rating: " + i.getRating());
//			System.out.println("Price: " + i.getPrice());
//			System.out.println("Description: " + i.getItemDescription());
//			System.out.println("Discount Available: " + i.getDiscount());
//			// i.getItemDescription().contains("ConsumerItem")
//
//			if (i instanceof ConsumerItem) {
//				ConsumerItem ci = (ConsumerItem) i;
//				System.out.println("Size: " + ci.getSize());
//				System.out.println("Weight: " + ci.getWeight());
//			} else if (i instanceof MediaItem) {
//				MediaItem mi = (MediaItem) i;
//				System.out.println("Size: " + mi.getSize());
//				System.out.println("Duration: " + mi.getDuration());
//				System.out.println("Quality: " + mi.getQuality());
//			} else {
//				System.out.println("Item has no type");
//			}
//
//			System.out
//					.println("************************************************************");
//		}
		// items.addAll(itemtemp);
	}

	public void addToRentList(Item item) {
		// TODO Auto-generated method stub
		item.setItemId(id++);
		rentitems.add(item);
	}

	public void addToBuyList(Item item) {
		// TODO Auto-generated method stub
		item.setItemId(id++);
		buyitems.add(item);
	}

	public void addToBidList(Item item) {
		// TODO Auto-generated method stub
		item.setItemId(id++);
		biditems.add(item);
	}

	@Override
	public void deleteItem(int itemId) {
		// TODO Auto-generated method stubItem it = null;
		String list = findItemInList(itemId);
		if (list == "rent") {
			for (Item i : rentitems) {
				if (i.getItemId() == itemId) {
					rentitems.remove(i);
					break;
					// items.remove(i);
				}
			}
		} else if (list == "buy") {
			for (Item i : buyitems) {
				if (i.getItemId() == itemId) {
					buyitems.remove(i);
					break;
					// items.remove(i);
				}
			}
		} else if (list == "bid") {
			for (Item i : biditems) {
				if (i.getItemId() == itemId) {
					biditems.remove(i);
					break;
					// items.remove(i);
				}
			}
		} else
			System.out.println("Item not found");
	}

	@Override
	public Item viewItem(int itemId) {
		Item i = getItem(itemId);
		if (i != null) {
			System.out
					.println("************************************************************");
			System.out.println("Item Id :" + i.getItemId());
			System.out.println("Item Title: " + i.getItemTitle());
			System.out.println("Number of Items Available: " + i.getQuantity());
			System.out.println("Seller Name: " + i.getSellerName());
			System.out.println("Item Rating: " + i.getRating());
			System.out.println("Price: " + i.getPrice());
			System.out.println("Description: " + i.getItemDescription());
			System.out.println("Discount Available: " + i.getDiscount());
			System.out
					.println("************************************************************");
			return i;
		} else {
			System.out.println("Item not found");
			return null;
		}
	}

	@Override
	public Item getItem(int itemId) {
		for (Item i : rentitems) {
			if (i.getItemId() == itemId) {
				return i;
			}
		}
		for (Item i : buyitems) {
			if (i.getItemId() == itemId) {
				return i;
			}
		}
		for (Item i : biditems) {
			if (i.getItemId() == itemId) {
				return i;
			}
		}
		return null;
	}

	@Override
	public void updateItem(int itemId, String title, float price, float discount) {
		Item temp = null;

		String list = findItemInList(itemId);
		if (list == "rent") {
			for (Item i : rentitems) {
				if (i.getItemId() == itemId) {
					temp = i;
					temp.setItemTitle(title);
					temp.setPrice(price);
					temp.setDiscount(discount);
					rentitems.remove(i);
					rentitems.add(temp);
					break;
				}
			}
		} else if (list == "buy") {
			for (Item i : buyitems) {
				if (i.getItemId() == itemId) {
					temp = i;
					temp.setItemTitle(title);
					temp.setPrice(price);
					temp.setDiscount(discount);
					buyitems.remove(i);
					buyitems.add(temp);
					break;
				}
			}
		} else if (list == "bid") {
			for (Item i : biditems) {
				if (i.getItemId() == itemId) {
					temp = i;
					temp.setItemTitle(title);
					temp.setPrice(price);
					temp.setDiscount(discount);
					biditems.remove(i);
					biditems.add(temp);
					break;
				}
			}
		}
		// items.add(temp);
		else
			System.out.println("Item not found");

	}

	private String findItemInList(int itemId) {
		// TODO Auto-generated method stub
		String list = null;
		for (Item i : rentitems) {
			if (i.getItemId() == itemId) {
				list = "rent";
			}
		}
		for (Item i : buyitems) {
			if (i.getItemId() == itemId) {
				list = "buy";
			}
		}
		for (Item i : biditems) {
			if (i.getItemId() == itemId) {
				list = "bid";
			}
		}
		return list;
	}

	@Override
	public boolean getItemStatus(int itemId) {
		Item i = getItem(itemId);
		if (i != null && i.getQuantity() > 0) {
			return true;
		}
		return false;
	}

	public void updateQuantity(int itemId, int quantity) {
		int oldQuantity = 0;

		Item item = getItem(itemId);

		if (item != null) {
			oldQuantity = item.getQuantity();
			item.setQuantity(quantity);
			System.out.println("ITEM INVENTORY update...");
			System.out.println("QUANTITY for Item ID: " + itemId
					+ " has been updated from " + oldQuantity + " to "
					+ quantity);
		} else {
			System.out.println("No item found.");
		}

	}

	@Override
	public List<Item> listItem(ItemType type) {
		List<Item> i;
		switch (type) {
		case BIDABLE:
			i = biditems;
			break;
		case RENTABLE:
			i = rentitems;
			break;
		case BUYABLE:
			i = buyitems;
			break;
		default:
			i = buyitems;
			break;
		}
		return i;
	}
}
