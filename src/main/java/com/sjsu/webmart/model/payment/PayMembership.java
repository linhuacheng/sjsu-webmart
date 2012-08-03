package com.sjsu.webmart.model.payment;

import java.math.BigDecimal;


public class PayMembership extends PaymentInfo {

	@Override
	public boolean processPayment(BigDecimal amount) {
		return this.debit(amount);
	}

}
