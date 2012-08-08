package com.sjsu.webmart.model.report;

import static com.sjsu.webmart.util.ConsoleUtil.printText;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.OrderServiceImpl;

public class OrderReport extends Report{

	OrderService orderService = OrderServiceImpl.getInstance();
	List<Order> orders = new ArrayList<Order>();
	
	public void showReport(OrderFilter filter)
	{
		super.showReport();
		
		orders = orderService.findOrders(filter);

		generateSubject();
		generateContent();
	}
	
	public void generateSubject(){
		setSubject("ORDER REPORT");
		super.generateSubject();
	}
	
	public void generateContent(){
		
		super.generateContent();
		String format = "|%1$-15s|%2$-15s|%3$-50s|%4$-20s|\n";
		System.out
				.println("__________________________________________________________________________________________________________");
		System.out.format(format, "ORDER ID", "TYPE", "ITEM", "COST");
		System.out
				.println("__________________________________________________________________________________________________________");
		for (Order order : orders) {
			System.out.format(format, order.getOrderId(), order.getOrderType(),
					order.getItem().getItemDescription(), order.getCost());
		}
		System.out
				.println("__________________________________________________________________________________________________________");
	}
}
