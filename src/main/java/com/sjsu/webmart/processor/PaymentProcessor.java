package com.sjsu.webmart.processor;

import java.math.BigDecimal;

import com.sjsu.webmart.model.payment.PaymentInfo;

public interface PaymentProcessor {
	
	public boolean debit(PaymentInfo paymentInfo, BigDecimal amount);
	public boolean credit(PaymentInfo paymentInfo, BigDecimal amount);
}

