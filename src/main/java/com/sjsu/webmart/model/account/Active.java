package com.sjsu.webmart.model.account;

public class Active implements AccountState{

	
	public Active() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean enable(Account ac) {
		// TODO Auto-generated method stub

		System.out.println("Account is already active");
		return false;
	}
	
	@Override
	public boolean suspend(Account ac) {
		// TODO Auto-generated method stub
		
		ac.setState(new InActive());
		System.out.println("Account is suspended");
		return true;
	}

	public String toString()
	{
		return "Account is Active";
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}

}
