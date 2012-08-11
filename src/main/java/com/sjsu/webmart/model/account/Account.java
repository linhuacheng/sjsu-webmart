package com.sjsu.webmart.model.account;


import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.notification.Message;
import com.sjsu.webmart.model.notification.MessageObservable;
import com.sjsu.webmart.model.notification.MessageObserver;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.service.NotificationService;
import com.sjsu.webmart.service.impl.NotificationServiceImpl;

public class Account implements MessageObservable{


	protected static NotificationService notificationService = NotificationServiceImpl.getInstance();
	private List<MessageObserver> observers = new ArrayList<MessageObserver>();
	
	private AccountState state;

	private int accountId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private AccountType accountType;
	
	List<PaymentInfo> paymentInfo;
	
	List<AddressInfo> addressInfo;

	public Account()
	{
		addObserver(notificationService);
	}
	
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

	public void suspendUser()
	{
		boolean check = false;
		state = getState();
		check = state.suspend(this);
		if(check)
		sendNotification("SUSPENDED");
	}

	public void enableUser()
	{
		boolean check = false;
		state = getState();
		check = state.enable(this);
		if(check)
		sendNotification("ACTIVATED");
	}

	@Override
	public void addObserver(MessageObserver observer) {
		// TODO Auto-generated method stub
		observers.add(observer);
	}

	@Override
	public void deleteObserver(MessageObserver observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(Object args) {
		// TODO Auto-generated method stub
		for (MessageObserver observer : observers) {
			observer.update(this, args);
		}
	}

	public void sendNotification(String status) {
		String content = "Account is "+status;
		Message msg = new Message(email, content);
		notifyObservers(msg);
	}

    public String getName(){
        return firstName + " " + lastName;
    }
}
