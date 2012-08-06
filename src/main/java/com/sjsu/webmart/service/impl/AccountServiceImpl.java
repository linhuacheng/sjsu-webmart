package com.sjsu.webmart.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.Active;
import com.sjsu.webmart.model.account.AddressInfo;
import com.sjsu.webmart.model.payment.PayMerchandise;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.service.AccountService;

public class AccountServiceImpl implements AccountService{

	private static AccountServiceImpl instance = null;
	private static List<Account> accounts = new ArrayList<Account>();
	private static int id = 1;
	private Account a;
	private List<AddressInfo> addresses;
//	private List<PaymentInfo> payment_details;
	
	
	private AccountServiceImpl() {
		// TODO Auto-generated constructor stub
		System.out.println("Constructor initialized");

		createInitialAccounts();

	}

	
	public static AccountService getInstance() {
	if (instance == null) {
	synchronized (AccountServiceImpl.class){
	if (instance == null) {
	instance = new AccountServiceImpl();
	}
	}
	}
	return instance;
	}
	
	
	@Override
	public void registerUser() {
		// TODO Auto-generated method stub
		a = new Account();
		AddressInfo addInfo;
		addresses = new ArrayList<AddressInfo>();
		String input;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			a.setAccountId(id++);
			
			System.out.println("Enter Password : ");
			while((input=br.readLine()).isEmpty())
				System.out.println("Please enter password : ");
			a.setPassword(input);
			
			System.out.println("Enter First Name : ");
			while((input=br.readLine()).isEmpty())
				System.out.println("Please enter first name : ");
			a.setFirstName(input);
			
			System.out.println("Enter Last Name : ");
			while((input=br.readLine()).isEmpty())
				System.out.println("Please enter last name : ");
			a.setLastName(input);
			
			System.out.println("Enter Email Address : ");
			while((input=br.readLine()).isEmpty())
				System.out.println("Please enter Email Address : ");
			a.setEmail(input);
			
			System.out.println("Enter Account Type : ");
			if((input=br.readLine())==null)
				a.setAccountType("Consumer");
			else
				a.setAccountType(input);
			
			while(true)
			{
				addInfo = new AddressInfo();
				
				System.out.println("Enter Address Line 1 : ");
				while((input=br.readLine()).isEmpty())
					System.out.println("Please enter Address Line 1 : ");
				addInfo.setAddress1(input);
				
				System.out.println("Enter Address Line 2 : ");
				addInfo.setAddress2(br.readLine());
				
				System.out.println("Enter City : ");
				while((input=br.readLine()).isEmpty())
					System.out.println("Please enter Address Line 1 : ");
				addInfo.setCity(input);
				
				System.out.println("Enter State : ");
				while((input=br.readLine()).isEmpty())
					System.out.println("Please enter State : ");
				addInfo.setState(input);
				
				System.out.println("Enter Zip : ");
				while((input=br.readLine()).isEmpty())
					System.out.println("Please enter Zip : ");
				addInfo.setZip(input);
				
				System.out.println("Enter Country : ");
				while((input=br.readLine()).isEmpty())
					System.out.println("Please enter Country : ");
				addInfo.setCountry(input);
				
				addresses.add(addInfo);
				
				System.out.println("Do you want to add one more address (Y/N) : ");
				if(br.readLine().startsWith("n"))
					break;
			}
			
			a.setAddressInfo(addresses);
			a.setPaymentInfo(null);
			a.setState(new Active());
			accounts.add(a);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception");
		}
		
		System.out.println(a.getAccountId());
		
		System.out.println("User Registered...");
	}

	
	@Override
	public void viewAccount(int accountId) {
		// TODO Auto-generated method stub
	
		Account a;
		a = findAccountById(accountId);
		int i = 1;
		if(a!=null)
		{
			System.out.println("Account Status : "+a.getState().toString());
			System.out.println("Account Id : "+a.getAccountId());
			System.out.println("First Name :  "+a.getFirstName());
			System.out.println("Last Name : "+a.getLastName());
			System.out.println("Email Id : "+a.getEmail());
			
			for(AddressInfo a_info : a.getAddressInfo())
			{
				System.out.println("Address "+i+" : ");
				System.out.println(a_info.getAddress1());
				System.out.println(a_info.getAddress2());
				System.out.println(a_info.getCity());
				System.out.println(a_info.getState());
				System.out.println(a_info.getZip());
				System.out.println(a_info.getCountry()+"\n");
			}
			
//			System.out.println(a.getPaymentInfo());
		}
		
		else
		{
			System.out.println("User not found !!");
		}
		
	}

	@Override
	public void editAccount(int accountId) {
		
		Account ac = findAccountById(accountId);
		String input;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Editing account...");
		
		if(ac!=null)
		{
			try {
				
				System.out.println("Enter New Password : ");
				if((input=br.readLine()).isEmpty());
				else
					ac.setPassword(input);
				
				System.out.println("Edit Email Address : ");
				System.out.println("Old Email : "+ac.getEmail());
				System.out.println("New Email : ");
				if((input=br.readLine()).isEmpty());
				else
					ac.setEmail(input);
				
				for(AddressInfo a_info : ac.getAddressInfo())
				{
					System.out.println("Edit Address Info : ");
					System.out.println("Old Street Address 1 : "+a_info.getAddress1());
					System.out.println("New Street Address 1 : ");
					if((input=br.readLine()).isEmpty());
					else
						a_info.setAddress1(input);
					
					System.out.println("Old Street Address 2 : "+a_info.getAddress2());
					System.out.println("New Street Address 2 : ");
					if((input=br.readLine()).isEmpty());
					else
						a_info.setAddress2(input);
					
					System.out.println("Old City Name : "+a_info.getCity());
					System.out.println("New City Name : ");
					if((input=br.readLine()).isEmpty());
					else
						a_info.setCity(input);
					
					System.out.println("Old State Name : "+a_info.getState());
					System.out.println("New State Name : ");
					if((input=br.readLine()).isEmpty());
					else
						a_info.setState(input);
					
					System.out.println("Old Zip Code : "+a_info.getZip());
					System.out.println("New Zip Code : ");
					if((input=br.readLine()).isEmpty());
					else
						a_info.setZip(input);
					
					System.out.println("Old Country : "+a_info.getCountry());
					System.out.println("New Country : ");
					if((input=br.readLine()).isEmpty());
					else
						a_info.setCountry(input);
					
				}
				
//				updateAccount(ac.getAccountId(), ac);

			} catch (Exception e) {
				// TODO: handle exception
			}
					}
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAccount(int accountId) {
		// TODO Auto-generated method stub
		
		Account ac = findAccountById(accountId);
		accounts.remove(ac);
		System.out.println("Account deleted");
	}


	@Override
    public Account findAccountById(int accountId) {
		// TODO Auto-generated method stub
		for(Account ac: accounts)
		{
//			System.out.println("Account Id from existing accounts .... "+ac.getAccountId());
//			System.out.println("Account Id from the user ... "+accountId);
			
			if(ac.getAccountId()==accountId)
				return ac;
		}
		
		System.out.println("User not found...");
		return null;
	}

//	public void updateAccount(int accountId, Account a) {
//		
//		for(Account ac: accounts)
//		{
//			if(ac.getAccountId() == accountId)
//				ac = a;
//		}
//	}

	@Override
	public void processEnableUserAccount(int accountId) {
		// TODO Auto-generated method stub
		Account ac = findAccountById(accountId);
		a.enableUser(ac);
	}

	@Override
	public void processSuspendUserAccount(int accountId) {
		// TODO Auto-generated method stub
		Account ac = findAccountById(accountId);
		a.suspendUser(ac);
	}


	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return accounts;
	}

	private void createInitialAccounts(){
		
		AddressInfo a_info = new AddressInfo();
//		PaymentInfo p = null;
//		payment_details = new ArrayList<PaymentInfo>();
		addresses = new ArrayList<AddressInfo>();
		
		a = new Account();

		a.setAccountId(id++);
		a.setAccountType("Customer");
		a.setEmail("nikitha@gmail.com");
		a.setFirstName("Nikitha");
		a.setLastName("Vurumalla");
		a.setPassword("nikitha");
		a.setPaymentInfo(null);
		a.setState(new Active());
		
		a_info.setAddress1("1234 Pebble Dr");
		a_info.setAddress2("#345");
		a_info.setCity("Sunnyvale");
		a_info.setState("CA");
		a_info.setZip("95039");
		a_info.setCountry("USA");
		addresses.add(a_info);
		a.setAddressInfo(addresses);

		
		PaymentInfo p_info1 = new PayMerchandise();
		p_info1.setPaymentInfoId(PaymentInfo.getNextId());
		p_info1.setCardNumber("5432123456782345");
		p_info1.setChequeNumber("45234456456");
		p_info1.setExpirationDate(new Date());
		
		PaymentInfo p_info2 = new PayMerchandise();
		p_info2.setPaymentInfoId(PaymentInfo.getNextId());
		p_info2.setCardNumber("5432123456788332");
		p_info2.setChequeNumber("45234451234");
		p_info2.setExpirationDate(new Date());
		
		List<PaymentInfo> payments = new ArrayList<PaymentInfo>();
		payments.add(p_info1);
		payments.add(p_info2);
		
		a.setPaymentInfo(payments);
		
		accounts.add(a);
	}
	
}
