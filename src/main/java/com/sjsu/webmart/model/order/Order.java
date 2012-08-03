package com.sjsu.webmart.model.order;

import java.math.BigDecimal;
import java.util.Date;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.processor.FulfillmentContext;
import com.sjsu.webmart.processor.PaymentProcessor;
import com.sjsu.webmart.processor.impl.CardProcessor;
import com.sjsu.webmart.processor.impl.ChequeProcessor;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public abstract class Order {

	private static int idSeq = 0;

	protected static InventoryService inventoryService = InventoryServiceImpl
			.getInstance();

	protected Integer orderId;
	protected OrderType orderType;
	protected OrderStatus orderStatus;
	protected Item item;
	protected Account account;
	protected Date fromDate;
	protected Date toDate;

	protected PaymentInfo paymentInfo;

	public Order() {
		idSeq++;
		orderId = idSeq;
	}

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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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

	public void processOrder(OrderParams orderParams) {

		if (itemAvailable()) {
			boolean paymentSucccess = processPayment(orderParams);
			fulfillOrder(orderParams);
			updateOrder();
			updateInventory();
			sendNotification();
		}
	}

	public boolean processPayment(OrderParams orderParams) {
		BigDecimal cost = calculateCost(orderParams);

		PaymentInfo paymentInfo = account.getPaymentInfo().get(0);
		PaymentProcessor paymentProcessor = getPaymentProcessor(orderParams
				.getPaymentType());
		paymentInfo.setPaymentProcessor(paymentProcessor);

		return paymentInfo.processPayment(cost);
	}

	private PaymentProcessor getPaymentProcessor(PaymentType paymentType) {
		switch (paymentType) {
		case CARD:
			return new CardProcessor();
		case CHEQUE:
			return new ChequeProcessor();
		default:
			return new CardProcessor();
		}
	}

	public void fulfillOrder(OrderParams orderParams) {
		FulfillmentContext context = new FulfillmentContext();
		context.shipOrder(orderParams);
	}

	@Override
	public String toString() {
		return "Order{" + "orderId=" + orderId + ", orderType=" + orderType
				+ ", item=" + item + ", buyer=" + account + ", seller="
				+ item.getSellerName() + '}';
	}

	public abstract boolean itemAvailable();

	public abstract BigDecimal calculateCost(OrderParams orderParams);

	public abstract void updateOrder();

	public abstract void updateInventory();

	public void sendNotification() {
		// send notification
	}
}
