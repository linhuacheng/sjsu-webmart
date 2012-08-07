package com.sjsu.webmart.model.account;


import java.util.List;
import com.sjsu.webmart.model.payment.PaymentInfo;

public class Account {


	private AccountState state;


	public boolean isValidAccount()
	{
		return (Boolean) null;
	}

	private int accountId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private AccountType accountType;
	
	List<PaymentInfo> paymentInfo;
	
	List<AddressInfo> addressInfo;

	
	public AccountState getState() {
		return state;
	}

	public void setState(AccountState state) {
		this.state = state;
	}

	public List<AddressInfo> getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(List<AddressInfo> addressInfo) {
		this.addressInfo = addressInfo;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public List<PaymentInfo> getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(List<PaymentInfo> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public boolean isValidAccount(int accountId)
	{
		return (Boolean) null;
	}
	
	public void suspendUser(Account ac)
	{
		state = ac.getState();
		state.suspend(ac);
	}

	public void enableUser(Account ac)
	{
		state = ac.getState();
		state.enable(ac);
	}
	
}
