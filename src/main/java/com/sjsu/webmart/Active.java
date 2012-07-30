package com.sjsu.webmart;

public class Active implements AccountState{

	private Account account;
	
	public Active(Account acc)
	{
		account = acc;
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		
		System.out.println("Account is created and is active");
		
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
		System.out.println("Account is already active");
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
		System.out.println("");
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
		System.out.println("Account is activated");
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
}
