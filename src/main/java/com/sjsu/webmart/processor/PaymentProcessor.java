package com.sjsu.webmart.processor;

import java.math.BigDecimal;

import com.sjsu.webmart.model.payment.PaymentInfo;

public interface PaymentProcessor {
	
	public void debit(PaymentInfo paymentInfo, BigDecimal amount);
	public void credit(PaymentInfo paymentInfo, BigDecimal amount);
}

