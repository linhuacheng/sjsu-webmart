package com.sjsu.webmart.model.account;

public interface AccountState {

	public boolean suspend(Account ac);
	public boolean enable(Account ac);
	public boolean isActive();
}
