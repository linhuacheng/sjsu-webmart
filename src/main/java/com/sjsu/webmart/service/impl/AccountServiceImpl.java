package com.sjsu.webmart.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.AccountType;
import com.sjsu.webmart.model.account.Active;
import com.sjsu.webmart.model.account.AddressInfo;
import com.sjsu.webmart.model.payment.PayMerchandise;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.util.ConsoleUtil;
import org.apache.commons.collections.CollectionUtils;

public class AccountServiceImpl implements AccountService{

	private static AccountServiceImpl instance = null;
	private static List<Account> accounts = new ArrayList<Account>();
	private static int id = 1;
	private Account a;
	private List<AddressInfo> addresses;
	private List<PaymentInfo> payment_details;
	private static SimpleDateFormat MONTH_YEAR_FORMAT = new SimpleDateFormat(
			"MM/yyyy");
	
	private AccountServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static AccountService getInstance() {
	if (instance == null) {
	synchronized (AccountServiceImpl.class){
	if (instance == null) {
	instance = new AccountServiceImpl();
	}}
	}
	return instance;
	}
	
	@Override
	public void registerUser() {
		// TODO Auto-generated method stub
		a = new Account();
		AddressInfo addInfo;
		addresses = new ArrayList<AddressInfo>();
		payment_details = new ArrayList<PaymentInfo>();
		String input;
		Date expDate;
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
			
			System.out.println("Enter Account Type (buyer/seller) : ");
			input = br.readLine();
			if((input==null) || input.equalsIgnoreCase("buyer"))
				a.setAccountType(AccountType.BUYER);
			else
				a.setAccountType(AccountType.SELLER);
			
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
				
				System.out.println("Do you want to add one more address (y/n) : ");
				if(br.readLine().startsWith("n"))
					break;
			}
			while(true)
			{
				PaymentInfo p_info = new PayMerchandise();

				System.out.println("Enter Payment Type (CARD/CHEQUE) : ");
				while((input=br.readLine()).isEmpty())
					System.out.println("Please enter payment type : ");
				
				if(input.equalsIgnoreCase("card"))
				{
					p_info.setPaymentType(PaymentType.CARD);
					p_info.setPaymentInfoId(PaymentInfo.getNextId());
				
					System.out.println("Enter Card Number : ");
					while((input=br.readLine()).isEmpty())
						System.out.println("Please enter card number : ");
					p_info.setCardNumber(input);
				
					System.out.println("Enter Expiration Date (MM/DD/YYYY) : ");
					while((expDate=ConsoleUtil.getDateValue(br)) == null)
						System.out.println("Please enter expiration date : ");
					p_info.setExpirationDate(expDate);
				
					System.out.println("Enter Security Code : ");
					while((input=br.readLine()).isEmpty())
						System.out.println("Please security code : ");
					int inputInt = Integer.parseInt(input);
					p_info.setSecurityCode(inputInt);
				}
				else if(input.equalsIgnoreCase("cheque"))
				{
					p_info.setPaymentType(PaymentType.CHEQUE);
					p_info.setPaymentInfoId(PaymentInfo.getNextId());
				
					System.out.println("Enter Cheque Number : ");
					while((input=br.readLine()).isEmpty())
						System.out.println("Please enter cheque number : ");
					p_info.setChequeNumber(input);
					
					p_info.setExpirationDate(new Date());
				}
				else
				{
					System.out.println("Incorrect input");
					continue;
				}
				System.out.println("Before adding p_info to payment_details");
				payment_details.add(p_info);
				System.out.println("Do you want to add one more payment info? (Y/N) : ");
				if(br.readLine().startsWith("n"))
					break;
				
			}
			
			a.setAddressInfo(addresses);
			a.setPaymentInfo(payment_details);
			a.setState(new Active());
			accounts.add(a);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		System.out.println("User Registered with Account Id : " + a.getAccountId());
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
			
			if(a.getPaymentInfo()!=null)
			{
				for(PaymentInfo p_info : a.getPaymentInfo())
				{
					System.out.println(p_info.getPaymentType());
					if(p_info.getPaymentType().equals(PaymentType.CARD)){
						System.out.println(p_info.getCardNumber());
						System.out.println(MONTH_YEAR_FORMAT.format(p_info.getExpirationDate())+"\n");
					}
					else{
						System.out.println(p_info.getChequeNumber()+"\n");
					}
				}
			}
		}
		
		else{
			System.out.println("User not found !!");
		}
		
	}

	@Override
	public void editPassowrd(int accountId) {
		
		Account ac = findAccountById(accountId);
		String input;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		if(ac!=null)
		{
			try {
				
				System.out.println("Enter New Password : ");
				if((input=br.readLine()).isEmpty());
				else
					ac.setPassword(input);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	

	@Override
	public void editAddressInfo(int accountId) {
		// TODO Auto-generated method stub

		Account ac = findAccountById(accountId);
		String input;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Editing Address Information...");
		
		if(ac!=null)
		{
			try {
				
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
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}


	@Override
	public void editPaymentInfo(int accountId) {
		// TODO Auto-generated method stub
		
		Account ac = findAccountById(accountId);
		String input;
		Date expDate;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Editing Payment Information...");
		
		if(ac!=null)
		{
			try {
				
				for(PaymentInfo p_info : ac.getPaymentInfo())
				{
					System.out.println("Edit Payment Info : ");
					
					if(p_info.getPaymentType().equals(PaymentType.CARD))
					{
						System.out.println("Old Card Number : "+p_info.getCardNumber());
						System.out.println("New Card Number : ");
						if((input=br.readLine()).isEmpty());
						else
							p_info.setCardNumber(input);
						
						System.out.println("Old Expiration Date (MM/DD/YYYY) : "+p_info.getExpirationDate());
						System.out.println("New Expiration Date (MM/DD/YYYY) : ");
						if((expDate=ConsoleUtil.getDateValue(br)) == null);
						else
							p_info.setExpirationDate(expDate);
					
						System.out.println("Old Security Code : "+p_info.getSecurityCode());
						System.out.println("New Security Code : ");
						if((input=br.readLine()).isEmpty());
						else
						{
							int inputInt = Integer.parseInt(input);
							p_info.setSecurityCode(inputInt);
						}
					}
					
					else 
					{
						System.out.println("Old Cheque Number : "+p_info.getChequeNumber());
						System.out.println("New Cheque Number : ");
						if((input=br.readLine()).isEmpty());
						else
							p_info.setChequeNumber(input);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
//			sendNotification(ac.getEmail());
		}
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


	@Override
	public void processEnableUserAccount(int accountId) {
		// TODO Auto-generated method stub
		Account ac = findAccountById(accountId);
		ac.enableUser();
	}

	@Override
	public void processSuspendUserAccount(int accountId) {
		// TODO Auto-generated method stub
		Account ac = findAccountById(accountId);
		ac.suspendUser();
	}


	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return accounts;
	}

	@Override
	public boolean isSeller(int accountId) {
		// TODO Auto-generated method stub
		Account a = findAccountById(accountId);
		if(a!=null)
		{
			if(a.getAccountType().equals(AccountType.SELLER))
				return true;
			else
				return false;
		}
		else
			System.out.println("Invalid Account ID");
		return false;
	}

	
	@Override
	public List<Account> getAccountsByType(AccountType accountType) {
		// TODO Auto-generated method stub
		List<Account> sellerAccounts = new ArrayList<Account>();
		List<Account> buyerAccounts = new ArrayList<Account>();
		for(Account a: accounts)
		{
			if(a.getAccountType().equals(AccountType.SELLER))
				sellerAccounts.add(a);
			else
				buyerAccounts.add(a);
		}
		
		if(accountType.equals(AccountType.BUYER))
			return buyerAccounts;
		else
			return sellerAccounts;
	}


	@Override
	public int getNextId() {
		// TODO Auto-generated method stub
		return id++;
	}


	@Override
	public void addAccount(Account account) {
		// TODO Auto-generated method stub
		accounts.add(account);
	}
	
	public String getFirstNameLastName(int accountId){
		String fullname = null;
		for (Account i: accounts){
			if (i.getAccountId() == accountId){
				fullname = i.getFirstName()+" " +i.getLastName();
			}
		}
		return fullname;
	}


}
