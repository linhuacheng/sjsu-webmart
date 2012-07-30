package com.sjsu.webmart.model.account;

public interface AccountState {

	public void register();
	public void suspend();
	public void cancel();
	public void enable();
	public boolean isActive();
}
