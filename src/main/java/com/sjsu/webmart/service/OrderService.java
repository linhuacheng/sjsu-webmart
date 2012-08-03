package com.sjsu.webmart.service;

import java.util.List;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderParams;

public interface OrderService {

	public List<Order> listOrder();
	
	public Order placeOrder(OrderParams orderParams);
	
	public Order getOrder(Integer orderId);
	
	public Order updateOrder(Order order);
	
	public boolean removeOrder(Integer orderId);
	/*
	Mange Order	(Buyer)
	List Item
	Place Bid (Buyer)
	Buy Item
		Enter Payment Info
		Enter Shipping Info
	Rent Item
	*/
}
