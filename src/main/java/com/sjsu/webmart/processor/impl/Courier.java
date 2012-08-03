package com.sjsu.webmart.processor.impl;

import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.processor.Fulfillment;

public class Courier implements Fulfillment {

	@Override
	public void shipOrder(OrderParams orderParams) {
		System.out.println("Order shipped by Courier: "
				+ orderParams.getOrder());
	}

}
