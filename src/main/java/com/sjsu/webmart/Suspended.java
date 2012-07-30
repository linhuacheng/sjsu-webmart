package com.sjsu.webmart;

public class Suspended implements AccountState{

	private Account account;
	
	public Suspended(Account acc)
	{
		account = acc;
	}
	
	@Override
	public void register() {
		// TODO Auto-generated method stub
		
		/* Store account object details in the database */
		
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
		/* Make the state of the account in database with account details suspended */
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
