package com.sjsu.webmart.model.order;

import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;

public class OrderParams {
	
	
	private Order order;
	
	private Bid bid;
	
	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
