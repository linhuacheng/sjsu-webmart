package com.sjsu.webmart.model.order;

public enum OrderStatus {
	VERIFY_ITEM, ITEM_AVAILABLE, ITEM_NOT_AVAILABLE, PAYMENT_FAIL, SHIPPED,
	PAYMENT_SUCCESSFUL, COMPLETED, RETURNED
}
