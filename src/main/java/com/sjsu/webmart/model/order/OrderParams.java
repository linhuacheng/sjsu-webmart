package com.sjsu.webmart.model.order;

import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;

public class OrderParams {
	
	private OrderType orderType;
	
	private Order order;
	
	private PaymentType paymentType;
	
	private FulfillmentType fulfillmentType;
	
	private PaymentInfo paymentInfo;

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public FulfillmentType getFulfillmentType() {
		return fulfillmentType;
	}

	public void setFulfillmentType(FulfillmentType fulfillmentType) {
		this.fulfillmentType = fulfillmentType;
	}

}
