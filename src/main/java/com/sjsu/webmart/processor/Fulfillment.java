package com.sjsu.webmart.processor;

import com.sjsu.webmart.model.order.OrderParams;

public interface Fulfillment {
	
	public void shipOrder(OrderParams orderParams);
}
