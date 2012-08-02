package com.sjsu.webmart.model.account;

public class InActive implements AccountState{

	@Override
	public void suspend(Account ac) {
		// TODO Auto-generated method stub
		System.out.println("Account is already suspended");
	}

	@Override
	public void enable(Account ac) {
		// TODO Auto-generated method stub
		ac.setState(new Active());
		System.out.println("Account is activated");
	}

}
