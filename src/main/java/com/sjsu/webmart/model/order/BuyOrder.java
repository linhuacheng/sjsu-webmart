package com.sjsu.webmart.model.order;

import java.math.BigDecimal;

import com.sjsu.webmart.model.item.Item;

public class BuyOrder extends Order {

	@Override
	public boolean itemAvailable() {
		boolean available = inventoryService.getItemStatus(item.getItemId());
		if (available) {
			System.out.println("Item ID: " + item.getItemId()
					+ " is AVAILABLE.");
		} else {
			System.out.println("Item ID: " + item.getItemId()
					+ " is NOT AVAILABLE.");
		}
		return available;
	}

	@Override
	public void updateOrder() {
		System.out.println("ORDER STATUS updated to: " + OrderStatus.COMPLETED);
		this.setOrderStatus(OrderStatus.COMPLETED);
	}

	@Override
	public void updateInventory() {
		Item i = inventoryService.getItem(item.getItemId());
		int quantity = i.getQuantity();
		quantity--;
		
		// deduct item quantity
		this.inventoryService.updateQuantity(item.getItemId(), quantity);
	}

	@Override
	public BigDecimal calculateCost(OrderParams orderParams) {
		BigDecimal cost = null;
		if (orderParams.getBid() != null) {
			cost = new BigDecimal(orderParams.getBid().getBidPrice());
		} else {
			cost = new BigDecimal(item.getPrice());
		}
		
		System.out.println("ORDER COST calculated: " + cost);
		return cost;
	}

}
