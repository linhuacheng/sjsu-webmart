package com.sjsu.webmart.service;

import java.util.List;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.model.order.RentPeriod;

public interface OrderService {

	public List<Order> listOrder();
		
	public List<Order> findOrders(OrderFilter filter);
	
	public Order placeOrder(OrderParams orderParams);
	
	public Order getOrder(Integer orderId);
	
	public Order updateOrder(Order order);
	
	public boolean removeOrder(Integer orderId);
	
	public boolean itemAvailable(Integer itemId, RentPeriod rentPeriod);
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
