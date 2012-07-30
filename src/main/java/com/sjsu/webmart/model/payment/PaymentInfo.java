package com.sjsu.webmart.model.payment;

import java.math.BigDecimal;
import com.sjsu.webmart.processor.PaymentProcessor;

import java.util.Date;

public abstract class PaymentInfo {

	protected Integer paymentInfoId;
	protected PaymentType paymentType;
	protected String cardNumber;
	protected Integer securityCode;
	protected Date expirationDate;
	protected PaymentProcessor paymentProcessor;

	public void debit(BigDecimal amount) {
		paymentProcessor.debit(this, amount);
	}
	public Integer getPaymentInfoId() {
		return paymentInfoId;
	}
	public void setPaymentInfoId(Integer paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Integer getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(Integer securityCode) {
		this.securityCode = securityCode;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public PaymentProcessor getPaymentProcessor() {
		return paymentProcessor;
	}
	public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
		this.paymentProcessor = paymentProcessor;
	}
	
	public void credit(BigDecimal amount) {
		paymentProcessor.credit(this, amount);	
	}

	public abstract void processPayment();
}
