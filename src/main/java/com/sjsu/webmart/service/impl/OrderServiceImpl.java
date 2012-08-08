package com.sjsu.webmart.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.model.order.OrderStatus;
import com.sjsu.webmart.model.order.OrderType;
import com.sjsu.webmart.model.order.RentPeriod;
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
		orderParams.getOrder().processOrder(orderParams);
		orders.add(orderParams.getOrder());
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

	
	@Override
	public List<Order> findOrders(OrderFilter filter) {
		List<Order> filteredOrder = new ArrayList<Order>();
		
		for (Order order:orders) {
			Integer accountId = filter.getAccountId();
			if (accountId != null && order.getAccount().getAccountId() != accountId)
				continue;
			Integer itemId = filter.getItemId();
			if (itemId != null && order.getItem().getItemId() != itemId)
				continue;
			Date start = filter.getStart();
			if (start != null) {
				Date startDt = getMidnight(start);
				if (order.getOrderDate().before(startDt))
					continue;
			}
			Date end = filter.getEnd();
			if (end != null) {
				Date endtDt = getNextDay(end);
				if (order.getOrderDate().after(endtDt))
					continue;
			}
			OrderStatus status = filter.getOrderStatus();
			if (status != null && order.getOrderStatus() != null && !order.getOrderStatus().equals(status)) 
				continue;
			OrderType type = filter.getOrderType();
			if (type != null && order.getOrderType()!= null && !order.getOrderType().equals(type)) 
				continue;
			// seems like everthing looks good
			filteredOrder.add(order);
			
		}
		return filteredOrder;
	}
	
	private Date  getMidnight(Date time) {
		Calendar date = new GregorianCalendar();
		date.setTime(time);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date.getTime();
	}
	
	private Date  getNextDay(Date time) {
		Calendar date = new GregorianCalendar();
		date.setTime(time);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);

		// next day
		date.add(Calendar.DAY_OF_MONTH, 1);
		return date.getTime();
	}
	
	public boolean itemAvailable(Integer itemId, RentPeriod rentPeriod) {
		OrderFilter filter = new OrderFilter();
		filter.setOrderType(OrderType.RENT);
		filter.setItemId(itemId);
		
		List<Order> orders = findOrders(filter);
		// Check the rent period
		for (Order order: orders) {
			if (order.getRentPeriod().contains(rentPeriod.getBegin()) || 
				order.getRentPeriod().contains(rentPeriod.getEnd())) {
				return false;
			}
		}
		
		return true;
	}

}
