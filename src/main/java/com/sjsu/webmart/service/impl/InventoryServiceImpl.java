package com.sjsu.webmart.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.service.InventoryService;

public class InventoryServiceImpl implements InventoryService {

	private static InventoryServiceImpl instance = null;
	private static int id = 0;
	private static List<Item> items = new ArrayList<Item>();
	private static List<Item> rentitems = new ArrayList<Item>();
	private static List<Item> biditems = new ArrayList<Item>();
	private static List<Item> buyitems = new ArrayList<Item>();

	private InventoryServiceImpl() {

	}

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

	public List<Item> listItem() {
		return items;
	}

	public int lastItemId() {
		int count = 0;
		for (Item i : items) {
			count++;
		}
		return count;
	}

	public void addItem(Item item) {
		Item i;
		// TODO Auto-generated method stub
		items.add(item);

		System.out.println("Item added");
		// System.out.println("Items : " +items.get(1));
	}

	public void viewAllItems() {
		// TODO Auto-generated method stub
		List<Item> itemtemp = new ArrayList<Item>();
		itemtemp.addAll(rentitems);
		itemtemp.addAll(biditems);
		itemtemp.addAll(buyitems);
		for (Item i : itemtemp) {
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
			// i.getItemDescription().contains("ConsumerItem")

			if (i.getItemDescription().contains("ConsumerItem")) {
				ConsumerItem ci = (ConsumerItem) i;
				System.out.println("Size: " + ci.getSize());
				System.out.println("Weight: " + ci.getWeight());
			} else {
				MediaItem mi = (MediaItem) i;
				System.out.println("Size: " + mi.getSize());
				System.out.println("Duration: " + mi.getDuration());
				System.out.println("Quality: " + mi.getQuality());
			}

			System.out
					.println("************************************************************");
		}
		items.addAll(itemtemp);
	}

	public void addToRentList(Item item) {
		// TODO Auto-generated method stub
		item.setItemId(++id);
		rentitems.add(item);
		System.out.println("Rentable Item");
	}

	public void addToBuyList(Item item) {
		// TODO Auto-generated method stub
		item.setItemId(++id);
		buyitems.add(item);
		System.out.println("Buyable Item");
	}

	public void addToBidList(Item item) {
		// TODO Auto-generated method stub
		item.setItemId(++id);
		biditems.add(item);
		System.out.println("Biddable Item");
	}

	@Override
	public void deleteItem(int itemId) {
		// TODO Auto-generated method stub
		Item it = null;
		for (Item i : items) {
			if (i.getItemId() == itemId) {
				it = i;
				break;
				// items.remove(i);
			}
		}
		for (Item i : rentitems) {
			if (i.getItemId() == itemId) {
				it = i;
				break;
				// items.remove(i);
			}
		}
		for (Item i : buyitems) {
			if (i.getItemId() == itemId) {
				it = i;
				break;
				// items.remove(i);
			}
		}
		for (Item i : buyitems) {
			if (i.getItemId() == itemId) {
				it = i;
				break;
				// items.remove(i);
			}
		}
		if (it != null) {
			items.remove(it);
			System.out.println("Item Deleted");
		} else
			System.out.println("Item not found");

	}

	@Override
	public Item viewItem(int itemId) {
		// TODO Auto-generated method stub
		// System.out.println("Items :" +items);
		for (Item i : items) {
			if (i.getItemId() == itemId) {
				System.out
						.println("************************************************************");
				System.out.println("Item Id :" + i.getItemId());
				System.out.println("Item Title: " + i.getItemTitle());
				System.out.println("Number of Items Available: "
						+ i.getQuantity());
				System.out.println("Seller Name: " + i.getSellerName());
				System.out.println("Item Rating: " + i.getRating());
				System.out.println("Price: " + i.getPrice());
				System.out.println("Description: " + i.getItemDescription());
				System.out.println("Discount Available: " + i.getDiscount());
				System.out
						.println("************************************************************");
				return i;
			}
		}
		System.out.println("Item not found");
		return null;
	}

	@Override
	public void updateItem(int itemId, String title, float price, float discount) {
		Item temp = null;
		for (Item i : items) {
			if (i.getItemId() == itemId) {
				temp = i;
				break;
			}
		}
		if (temp != null) {
			temp.setItemTitle(title);
			temp.setPrice(price);
			temp.setDiscount(discount);
			for (Item i : items) {
				if (i.getItemId() == itemId) {
					items.remove(i);
					break;
				}
			}
			String list = findItemInList(itemId);
			if (list == "rent") {
				for (Item i : rentitems) {
					if(i.getItemId() == itemId){
					rentitems.remove(i);
					rentitems.add(temp);
					break;
					}
				}
			}
			else if (list == "buy") {
				for (Item i : buyitems) {
					if(i.getItemId() == itemId){
						buyitems.remove(i);
						buyitems.add(temp);
						break;
						}
				}
			}
			else if (list == "bid") {
				for (Item i : biditems) {
					if(i.getItemId() == itemId){
						biditems.remove(i);
						biditems.add(temp);
						break;
						}
				}
			}
			items.add(temp);
			
			
		} else
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
		// TODO Auto-generated method stub
		for (Item i : items) {
			if ((i.getItemId() == itemId) && (i.getQuantity() > 0)) {
				return true;
			}
		}
		return false;
	}

	public void updateQuantity(int itemId, int quantity) {
		int oldQuantity = 0;

		for (Item item : items) {
			if (item.getItemId() == itemId) {
				oldQuantity = item.getQuantity();
				item.setQuantity(quantity);
			}
		}

		System.out.println("ITEM INVENTORY update...");
		System.out.println("QUANTITY for Item ID: " + itemId
				+ " has been updated from " + oldQuantity + " to " + quantity);

		for (Item item : rentitems) {
			if (item.getItemId() == itemId) {
				item.setQuantity(quantity);
				return;
			}
		}
		for (Item item : biditems) {
			if (item.getItemId() == itemId) {
				item.setQuantity(quantity);
				return;
			}
		}
		for (Item item : buyitems) {
			if (item.getItemId() == itemId) {
				item.setQuantity(quantity);
				return;
			}
		}
	}

}
