package com.sjsu.webmart.service;

import java.util.List;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.AccountType;

public interface AccountService {

	public void registerUser();
	
	public void viewAccount(int accountId);
	
	public void editAccount(int accountId);
	
	public void deleteAccount(int accountId);
	
	public void processEnableUserAccount(int accountId);
	
	public void processSuspendUserAccount(int accountId);
	
	public List<Account> getAllAccounts();

    Account findAccountById(int accountId);

	boolean isSeller(int accountId);

	List<Account> getAccountsByType(AccountType accountType);
}
