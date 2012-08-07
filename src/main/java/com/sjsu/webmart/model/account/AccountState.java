package com.sjsu.webmart.model.account;

public interface AccountState {

	public void suspend(Account ac);
	public void enable(Account ac);
	public boolean isActive();
}
