package com.sjsu.webmart.test;

import com.sjsu.webmart.model.order.BuyOrder;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.OrderServiceImpl;

public class TestOrder {
	
	public static void main(String[] args) {
		OrderService orderService = new OrderServiceImpl();
		
		
		Order buyOrder = new BuyOrder();
		buyOrder.setOrderId(123);
		
		
		orderService.placeOrder(buyOrder);
		
		for (Order o: orderService.listOrder()) {
			System.out.println("+++++++++++++++++++++");
			System.out.println("Order ID: " + o.getOrderId());
		}
		
	}
}
