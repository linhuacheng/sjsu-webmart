package com.sjsu.webmart.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private static List<Order> orders = new ArrayList<Order>();

	// singleton

	private static OrderServiceImpl instance = null;

	private OrderServiceImpl() {
	}

	public static OrderServiceImpl getInstance() {
		if (instance == null) {
			synchronized (OrderServiceImpl.class) {
				if (instance == null) {
					instance = new OrderServiceImpl();
				}
			}
		}
		return instance;
	}

	@Override
	public List<Order> listOrder() {
		return orders;
	}

	@Override
	public Order placeOrder(OrderParams orderParams) {
		orders.add(orderParams.getOrder());
		orderParams.getOrder().processOrder(orderParams);
		return orderParams.getOrder();
	}

	@Override
	public Order getOrder(Integer orderId) {
		for (Order o : orders) {
			if (o.getOrderId().equals(orderId))
				return o;
		}
		return null;
	}

	@Override
	public Order updateOrder(Order order) {
		for (Order o : orders) {
			if (o.getOrderId().equals(order.getOrderId()))
				orders.remove(o);
		}
		orders.add(order);
		return order;
	}

	@Override
	public boolean removeOrder(Integer orderId) {
		for (Order o : orders) {
			if (o.getOrderId().equals(orderId)) {
				orders.remove(o);
				return true;
			}

		}
		return false;
	}
}
