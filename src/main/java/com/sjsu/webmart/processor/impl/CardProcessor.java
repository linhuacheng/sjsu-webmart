package com.sjsu.webmart.processor.impl;

import java.math.BigDecimal;

import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.processor.PaymentProcessor;

public class CardProcessor implements PaymentProcessor {

	@Override
	public boolean debit(PaymentInfo paymentInfo, BigDecimal amount) {
		System.out
				.println("Credit card " + paymentInfo.getCardNumber()
						+ " has been successfully processed. Amount debited: "
						+ amount);
		return true;
	}

	@Override
	public boolean credit(PaymentInfo paymentInfo, BigDecimal amount) {
		System.out.println("Credit card " + paymentInfo.getCardNumber()
				+ " has been successfully processed. Amount credited: "
				+ amount);
		return true;
	}

}
