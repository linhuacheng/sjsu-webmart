package com.sjsu.webmart.model.order;

import java.math.BigDecimal;

public class BuyOrder extends Order {

	@Override
	public boolean itemAvailable() {
		boolean available = inventoryService.getItemStatus(item);
		if (available) {
			System.out.println("Item ID: " + item.getItemId()
					+ " is available.");
		} else {
			System.out.println("Item ID: " + item.getItemId()
					+ " is not available.");
		}
		return available;
	}

	@Override
	public void updateOrder() {
		this.setOrderStatus(OrderStatus.SHIPPED);
	}

	@Override
	public void updateInventory() {
		// deduct item quantity
		this.inventoryService.updateItem(item);

	}

	@Override
	public BigDecimal calculateCost(OrderParams orderParams) {
		return new BigDecimal(item.getBuyNowPrice());
	}

}
