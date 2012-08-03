package com.sjsu.webmart.model.order;

import com.sjsu.webmart.model.payment.PaymentType;

public class OrderParams {
	
	OrderType orderType;
	
	Order order;
	
	PaymentType paymentType;
	
	FulfillmentType fulfillmentType;

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
