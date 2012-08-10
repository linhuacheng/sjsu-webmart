package com.sjsu.webmart.model.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.util.ConsoleUtil;

public class RentOrder extends Order {

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
		// old implementation of rent order
		/*
		boolean available =  orderService.itemAvailable(item.getItemId(), rentPeriod);
		if (available) {
			System.out.println("Item ID: " + item.getItemId()
					+ " is AVAILABLE for " + rentPeriod);
		} else {
			System.out.println("Item ID: " + item.getItemId()
					+ " is NOT AVAILABLE for " + rentPeriod);
		}
		return available;
		*/
	}

	@Override
	public void updateOrder() {
		System.out.println("ORDER STATUS updated to:" + OrderStatus.RENTED);
		this.setOrderStatus(OrderStatus.RENTED);

	}

	@Override
	public void updateInventory() {
		// block off the date rented in the item
		//System.out.println("INVENTORY UPDATE: Block off the rented date.");
		Item i = inventoryService.getItem(item.getItemId());
		int quantity = i.getQuantity();
		quantity--;
		
		// deduct item quantity
		this.inventoryService.updateQuantity(item.getItemId(), quantity);
	}

	@Override
	public BigDecimal calculateCost(OrderParams orderParams) {
		
		if (rentPeriod != null  && rentPeriod.getBegin()!= null && rentPeriod.getEnd()!= null) {
			cost = calculateRentCost();			
		} else {
			cost = new BigDecimal(0);
			System.out.println("ORDER COST calculated: 0");	
		}
		
		return cost;
	}
		
	public BigDecimal calculateRentCost() {
		Date rentStart = rentPeriod.getBegin();
		Date rentEnd = rentPeriod.getEnd();
		int numDays = getDaysDiff(rentStart, rentEnd);

		BigDecimal cost = new BigDecimal(numDays * item.getPrice());
		
		System.out.println("ORDER COST calculated:" + ConsoleUtil.NF.format(cost));
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
	
	public void returnOrder(Date returnDate) {
		rentPeriod.setEnd(returnDate);
		
		OrderParams orderParams = new OrderParams();
		orderParams.setOrder(this);
		
		boolean paymentSucccess = processPayment(orderParams);
		if (paymentSucccess) {
			// Update order
			System.out.println("ORDER STATUS updated to:" + OrderStatus.RETURNED);
			this.setOrderStatus(OrderStatus.RETURNED);
			
			// Update inventory
			Item i = inventoryService.getItem(item.getItemId());
			int quantity = i.getQuantity();
			quantity++;
			
			// add item quantity
			inventoryService.updateQuantity(item.getItemId(), quantity);
		
			sendNotification();
			
			setOrderStatus(OrderStatus.RETURNED);
		}
	}

}
