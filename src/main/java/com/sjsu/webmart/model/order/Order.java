package com.sjsu.webmart.model.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.notification.Message;
import com.sjsu.webmart.model.notification.MessageObservable;
import com.sjsu.webmart.model.notification.MessageObserver;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.processor.FulfillmentContext;
import com.sjsu.webmart.processor.PaymentProcessor;
import com.sjsu.webmart.processor.impl.CardProcessor;
import com.sjsu.webmart.processor.impl.ChequeProcessor;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.NotificationService;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.service.impl.NotificationServiceImpl;
import com.sjsu.webmart.service.impl.OrderServiceImpl;
import com.sjsu.webmart.util.ConsoleUtil;

/**
 * @author chenglin
 *
 */
public abstract class Order implements MessageObservable {

	private static int idSeq = 0;

	protected static InventoryService inventoryService = InventoryServiceImpl
			.getInstance();
	
	protected static NotificationService notificationService = NotificationServiceImpl
			.getInstance();
	
	protected static OrderService orderService = OrderServiceImpl
			.getInstance();
	
	private List<MessageObserver> observers = new ArrayList<MessageObserver>();

	protected Integer orderId;
	protected OrderType orderType;
	protected OrderStatus orderStatus;
	protected Item item;
	protected Account account;
	protected RentPeriod rentPeriod;
	protected Date orderDate ;
	protected BigDecimal cost;

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	

	public Order() {
		idSeq++;
		orderId = idSeq;
		orderDate = new Date();
		addObserver(notificationService);
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

	public RentPeriod getRentPeriod() {
		return rentPeriod;
	}

	public void setRentPeriod(RentPeriod rentPeriod) {
		this.rentPeriod = rentPeriod;
	}

	public void processOrder(OrderParams orderParams) {
		System.out
				.println("****************** START PROCESSING ORDER ********************");
		if (itemAvailable()) {
			boolean paymentSucccess = processPayment(orderParams);
			if (paymentSucccess) {
				fulfillOrder(orderParams);
				updateOrder();
				updateInventory();
				sendNotification();
			}
		}
		System.out
				.println("******************** END PROCESSING ORDER *******************");
	}

	public boolean processPayment(OrderParams orderParams) {
		cost = calculateCost(orderParams);

		PaymentInfo paymentInfo = orderParams.getPaymentInfo();
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
		String start = (rentPeriod==null)? "": ConsoleUtil.SDF.format(rentPeriod.getBegin());
		String end = (rentPeriod==null)? "": ConsoleUtil.SDF.format(rentPeriod.getEnd());
		
		return "Order{" + "orderId=" + orderId + ", orderType=" + orderType
				+ ", item=" + item.getItemTitle() + ", quantity=" + item.getQuantity()
				+", buyer=" + account.getEmail() + ", seller="
				+ item.getSellerName() + ", start=" + start + ", end=" + end + "}";
	}

	public void addObserver(MessageObserver observer) {
		observers.add(observer);
	}

	public void deleteObserver(MessageObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers(Object args) {
		for (MessageObserver observer : observers) {
			observer.update(this, args);
		}
	}
	public abstract boolean itemAvailable();

	public abstract BigDecimal calculateCost(OrderParams orderParams);

	public abstract void updateOrder();

	public abstract void updateInventory();

	public void sendNotification() {
		String content = "ORDER PROCESSED: " + this.toString();
		Message msg = new Message(this.getAccount().getEmail(), content);
		notifyObservers(msg);
	}
}
