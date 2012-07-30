package com.sjsu.webmart.model.order;

import java.util.Date;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.payment.PaymentInfo;

public abstract class Order {
	
	protected Integer orderId;
	protected OrderType orderType;
	protected OrderStatus orderrStatus;
	protected Item item;
	protected Account account;
	protected Date fromDate;
	protected Date toDate;
	
	protected PaymentInfo paymentInfo;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public OrderStatus getOrderrStatus() {
		return orderrStatus;
	}
	public void setOrderrStatus(OrderStatus orderrStatus) {
		this.orderrStatus = orderrStatus;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public void processOrder() {
		
		if (itemAvailable()) {
			boolean paymentSucccess = processPayment();
			fulfillOrder();
			updateOrder();
			updateInventory();
			sendNotification();
		}
	}
	
	public abstract boolean itemAvailable();
	public abstract boolean processPayment();
	public abstract void fulfillOrder();
	public abstract void updateOrder();
	public abstract void updateInventory();
	public abstract void sendNotification();

}
