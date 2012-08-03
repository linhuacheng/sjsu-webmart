package com.sjsu.webmart.model.order;

import java.math.BigDecimal;


public class ReturnOrder extends Order {


	@Override
	public boolean itemAvailable() {
		return true;
	}


	@Override
	public void updateOrder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInventory() {
		// Add back to inventory
		
	}

	@Override
	public BigDecimal calculateCost(OrderParams orderParams) {
		// return negative cost
		return new BigDecimal(- item.getPrice());
	}

}
