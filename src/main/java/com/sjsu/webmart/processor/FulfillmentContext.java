package com.sjsu.webmart.processor;

import com.sjsu.webmart.model.order.FulfillmentType;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.processor.impl.Courier;
import com.sjsu.webmart.processor.impl.Online;
import com.sjsu.webmart.processor.impl.ShipToStore;

public class FulfillmentContext {

	private Fulfillment fulfillment;

	public void shipOrder(OrderParams orderParams) {
		fulfillment = getFulfillmentStrategy(orderParams);
		fulfillment.shipOrder(orderParams);
	}

	private Fulfillment getFulfillmentStrategy(OrderParams orderParams) {
		Fulfillment fulfillment;
		FulfillmentType fulfillmentType = orderParams.getOrder().getFulfillmentType();
		switch (fulfillmentType) {
		case COURIER:
			fulfillment = new Courier();
			break;
		case ONLINE:
			fulfillment = new Online();
			break;
		case STORE:
			fulfillment = new ShipToStore();
			break;
		default:
			fulfillment = new Courier();
			break;
		}

		return fulfillment;
	}
}
