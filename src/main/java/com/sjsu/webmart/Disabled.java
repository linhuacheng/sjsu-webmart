package main.java.com.sjsu.webmart;

public class Disabled implements AccountState{

	private Account account;
	
	public Disabled(Account acc)
	{
		account = acc;
	}
	
	@Override
	public void register() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
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
