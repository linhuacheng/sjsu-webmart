package com.sjsu.webmart.model.account;

public class InActive implements AccountState{

	@Override
	public boolean suspend(Account ac) {
		// TODO Auto-generated method stub
		System.out.println("Account is already suspended");
		return false;
	}

	@Override
	public boolean enable(Account ac) {
		// TODO Auto-generated method stub
		ac.setState(new Active());
		System.out.println("Account is activated");
		return true;
	}

	public String toString()
	{
		return "Account is Inactive";
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
}
