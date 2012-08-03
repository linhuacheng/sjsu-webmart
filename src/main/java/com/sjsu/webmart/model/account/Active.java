package com.sjsu.webmart.model.account;

public class Active implements AccountState{

	private Account account;
	
	public Active(Account acc)
	{
		account = acc;
	}

	public Active() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enable(Account ac) {
		// TODO Auto-generated method stub
		
		System.out.println("Account is already active");
		
	}
	
	@Override
	public void suspend(Account ac) {
		// TODO Auto-generated method stub
		
		ac.setState(new InActive());
		System.out.println("Account is suspended");
	}

	

}
