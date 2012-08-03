package com.sjsu.webmart.model.account;

import com.sjsu.webmart.model.payment.PaymentInfo;

public class Account {

	AccountState state;
	
	PaymentInfo paymentInfo;
	
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	
	public boolean isValidAccount()
	{
		return (Boolean) null;
	}
	
	public void registerUser()
	{
		state.register();
	}
	
	public void suspendUser()
	{
		state.suspend();
	}

	public void deleteUser()
	{
		state.cancel();
	}
	
	public void enableUser()
	{
		state.enable();
	}
	
	public void login()
	{
		
	}
	
	public void logout()
	{
		
	}

	public AccountState getState() {
		return state;
	}

	public void setState(AccountState state) {
		this.state = state;
	}
	
}
