package com.sjsu.webmart.model.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class RentOrder extends Order {

	@Override
	public boolean itemAvailable() {
		boolean available =  orderService.itemAvailable(item.getItemId(), rentPeriod);
		if (available) {
			System.out.println("Item ID: " + item.getItemId()
					+ " is AVAILABLE for " + rentPeriod);
		} else {
			System.out.println("Item ID: " + item.getItemId()
					+ " is NOT AVAILABLE for " + rentPeriod);
		}
		return available;
	}

	@Override
	public void updateOrder() {
		System.out.println("ORDER STATUS updated to:" + OrderStatus.COMPLETED);
		this.setOrderStatus(OrderStatus.COMPLETED);

	}

	@Override
	public void updateInventory() {
		// block off the date rented in the item
		System.out.println("INVENTORY UPDATE: Block off the rented date.");
	}

	@Override
	public BigDecimal calculateCost(OrderParams orderParams) {
		Date rentStart = orderParams.getOrder().getRentPeriod().getBegin();
		Date rentEnd = orderParams.getOrder().getRentPeriod().getEnd();
		int numDays = getDaysDiff(rentStart, rentEnd);

		BigDecimal cost = new BigDecimal(numDays * item.getPrice());
		
		System.out.println("ORDER COST calculated:" + cost);
		return cost;
	}

	private int getDaysDiff(Date start, Date end) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(start);
		calendar2.setTime(end);
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return (int)Math.ceil(diffDays);
	}

}
