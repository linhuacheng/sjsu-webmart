package com.sjsu.webmart.processor.impl;

import static com.sjsu.webmart.util.ConsoleUtil.NF;

import java.math.BigDecimal;

import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.processor.PaymentProcessor;

public class CardProcessor implements PaymentProcessor {

	@Override
	public boolean debit(PaymentInfo paymentInfo, BigDecimal amount) {
		System.out
				.println("CREDIT CARD: " + paymentInfo.getCardNumber()
						+ " has been successfully processed. Amount debited: "
						+ NF.format(amount));
		return true;
	}

	@Override
	public boolean credit(PaymentInfo paymentInfo, BigDecimal amount) {
		System.out.println("CREDIT CARD " + paymentInfo.getCardNumber()
				+ " has been successfully processed. Amount credited: "
				+ NF.format(amount));
		return true;
	}

}
