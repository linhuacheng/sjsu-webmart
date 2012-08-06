package com.sjsu.webmart.model.payment;

import java.math.BigDecimal;
import com.sjsu.webmart.processor.PaymentProcessor;

import java.util.Date;

public abstract class PaymentInfo {
	
	private static int idSeq = 1;

	protected Integer paymentInfoId;
	protected PaymentType paymentType;
	protected String cardNumber;
	protected String chequeNumber;


	protected Integer securityCode;
	protected Date expirationDate;
	protected PaymentProcessor paymentProcessor;

	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	
	public boolean debit(BigDecimal amount) {
		return paymentProcessor.debit(this, amount);
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
	
	public boolean credit(BigDecimal amount) {
		return paymentProcessor.credit(this, amount);	
	}

	public abstract boolean processPayment(BigDecimal amount);
	
	public static Integer getNextId() {
		return idSeq++;
	}
}
