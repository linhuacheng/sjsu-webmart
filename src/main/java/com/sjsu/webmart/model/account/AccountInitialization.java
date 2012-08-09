package com.sjsu.webmart.model.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sjsu.webmart.model.payment.PayMerchandise;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;

public class AccountInitialization {

	AccountService accountService = AccountServiceImpl.getInstance();
	
	@SuppressWarnings("deprecation")
	public void initializeAccount()
	{
		Account a1 = new Account();
		AddressInfo a_info1 = new AddressInfo();
		List<PaymentInfo>payments1 = new ArrayList<PaymentInfo>();
		List<AddressInfo> addresses1 = new ArrayList<AddressInfo>();
		Date d1 = new Date();
		d1.setMonth(10);
		d1.setYear(114);
		
		a1.setAccountId(accountService.getNextId());
		a1.setAccountType(AccountType.BUYER);
		a1.setEmail("nikitha@gmail.com");
		a1.setFirstName("Nikitha");
		a1.setLastName("Vurumalla");
		a1.setPassword("nikitha");
		a1.setState(new Active());
		
		a_info1.setAddress1("1234 Pebble Dr");
		a_info1.setAddress2("#345");
		a_info1.setCity("Sunnyvale");
		a_info1.setState("CA");
		a_info1.setZip("95039");
		a_info1.setCountry("USA");
		addresses1.add(a_info1);
		a1.setAddressInfo(addresses1);

		
		PaymentInfo p1_info1 = new PayMerchandise();
		p1_info1.setPaymentType(PaymentType.CARD);
		p1_info1.setPaymentInfoId(PaymentInfo.getNextId());
		p1_info1.setCardNumber("5432123456782345");
		p1_info1.setSecurityCode(123);
		p1_info1.setExpirationDate(d1);
		
		PaymentInfo p1_info2 = new PayMerchandise();
		p1_info2.setPaymentType(PaymentType.CHEQUE);
		p1_info2.setPaymentInfoId(PaymentInfo.getNextId());
		p1_info2.setChequeNumber("45234451234");
		p1_info2.setExpirationDate(d1);
		
		payments1.add(p1_info1);
		payments1.add(p1_info2);
		a1.setPaymentInfo(payments1);
		
		accountService.addAccount(a1);

		Account a2 = new Account();
		AddressInfo a_info2 = new AddressInfo();
		List<PaymentInfo>payments2 = new ArrayList<PaymentInfo>();
		List<AddressInfo> addresses2 = new ArrayList<AddressInfo>();
		Date d2 = new Date();
		d2.setMonth(3);
		d2.setYear(117);
		
		a2.setAccountId(accountService.getNextId());
		a2.setAccountType(AccountType.SELLER);
		a2.setEmail("priyanka@gmail.com");
		a2.setFirstName("Priyanka");
		a2.setLastName("Bhardwaj");
		a2.setPassword("priyanka");
		a2.setState(new Active());
		
		a_info2.setAddress1("5392 Flora Vista");
		a_info2.setAddress2("#242");
		a_info2.setCity("Santa Clara");
		a_info2.setState("CA");
		a_info2.setZip("95054");
		a_info2.setCountry("USA");
		addresses2.add(a_info2);
		a2.setAddressInfo(addresses2);

		
		PaymentInfo p2_info1 = new PayMerchandise();
		p2_info1.setPaymentType(PaymentType.CARD);
		p2_info1.setPaymentInfoId(PaymentInfo.getNextId());
		p2_info1.setCardNumber("1234567896782345");
		p2_info1.setSecurityCode(321);
		p2_info1.setExpirationDate(d2);
		
		payments2.add(p2_info1);
		a2.setPaymentInfo(payments2);
		
		accountService.addAccount(a2);
		
		
		Account a3 = new Account();
		AddressInfo a_info3 = new AddressInfo();
		List<PaymentInfo>payments3 = new ArrayList<PaymentInfo>();
		List<AddressInfo> addresses3 = new ArrayList<AddressInfo>();
		Date d3 = new Date();
		d3.setMonth(6);
		d3.setYear(115);
		
		a3.setAccountId(accountService.getNextId());
		a3.setAccountType(AccountType.SELLER);
		a3.setEmail("priyanka@gmail.com");
		a3.setFirstName("Priyanka");
		a3.setLastName("Bhardwaj");
		a3.setPassword("priyanka");
		a3.setState(new Active());
		
		a_info3.setAddress1("5392 Flora Vista");
		a_info3.setAddress2("#242");
		a_info3.setCity("Santa Clara");
		a_info3.setState("CA");
		a_info3.setZip("95054");
		a_info3.setCountry("USA");
		addresses3.add(a_info3);
		a3.setAddressInfo(addresses3);

		
		PaymentInfo p3_info1 = new PayMerchandise();
		PaymentInfo p3_info2 = new PayMerchandise();
		p3_info1.setPaymentType(PaymentType.CARD);
		p3_info1.setPaymentInfoId(PaymentInfo.getNextId());
		p3_info1.setCardNumber("679134567424345");
		p3_info1.setSecurityCode(352);
		p3_info1.setExpirationDate(d3);
		
		p3_info2.setPaymentType(PaymentType.CHEQUE);
		p3_info2.setPaymentInfoId(PaymentInfo.getNextId());
		p3_info2.setChequeNumber("96434451234");
		p3_info2.setExpirationDate(d3);
		
		payments3.add(p3_info1);
		a3.setPaymentInfo(payments3);
		
		accountService.addAccount(a3);

	}
}