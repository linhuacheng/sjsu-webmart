package com.sjsu.webmart.model.order;

import java.util.Date;

public class OrderFilter {

	private Integer accountId;
	
	private Date start;
	
	private Date end;
	
	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
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

	private OrderType orderType;
	
	private OrderStatus orderStatus;
}
