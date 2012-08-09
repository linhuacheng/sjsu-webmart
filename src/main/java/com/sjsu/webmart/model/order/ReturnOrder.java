package com.sjsu.webmart.model.order;

import java.math.BigDecimal;

import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.processor.FulfillmentContext;
import com.sjsu.webmart.util.ConsoleUtil;


public class ReturnOrder extends Order {


	@Override
	public boolean itemAvailable() {
		return true;
	}


	@Override
	public void updateOrder() {
		System.out.println("ORDER STATUS updated to:" + OrderStatus.COMPLETED);
		this.setOrderStatus(OrderStatus.COMPLETED);
	}

	@Override
	public void updateInventory() {
		// Add back to inventory
		Item i = inventoryService.getItem(item.getItemId());
		int quantity = i.getQuantity();
		quantity++;
		
		// deduct item quantity
		this.inventoryService.updateQuantity(item.getItemId(), quantity);
	}

	@Override
	public BigDecimal calculateCost(OrderParams orderParams) {
		if (cost == null) {
			cost = new BigDecimal(- item.getPrice());
		}
		// return negative cost
		System.out.println("ORDER COST calculated:" + ConsoleUtil.NF.format(cost.doubleValue()));
		return cost;
	}
	
	@Override
	public void fulfillOrder(OrderParams orderParams) {
		System.out.println("FULFILLMENT - do nothing for return order.");
	}

}
