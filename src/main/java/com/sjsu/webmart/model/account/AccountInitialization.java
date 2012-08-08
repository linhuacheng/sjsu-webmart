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
	
	public void initializeAccount()
	{
		Account a1 = new Account();
		AddressInfo a_info1 = new AddressInfo();
		List<PaymentInfo>payments1 = new ArrayList<PaymentInfo>();
		List<AddressInfo> addresses1 = new ArrayList<AddressInfo>();
		
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

		
		PaymentInfo p_info1 = new PayMerchandise();
		p_info1.setPaymentType(PaymentType.CARD);
		p_info1.setPaymentInfoId(PaymentInfo.getNextId());
		p_info1.setCardNumber("5432123456782345");
		p_info1.setSecurityCode(123);
		p_info1.setExpirationDate(new Date());
		
		PaymentInfo p_info2 = new PayMerchandise();
		p_info2.setPaymentType(PaymentType.CHEQUE);
		p_info2.setPaymentInfoId(PaymentInfo.getNextId());
		p_info2.setChequeNumber("45234451234");
		
		payments1.add(p_info1);
		payments1.add(p_info2);
		a1.setPaymentInfo(payments1);
		
		accountService.addAccount(a1);

		Account a2 = new Account();
		AddressInfo a_info2 = new AddressInfo();
		List<PaymentInfo>payments2 = new ArrayList<PaymentInfo>();
		List<AddressInfo> addresses2 = new ArrayList<AddressInfo>();
		
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

		
		PaymentInfo p_info3 = new PayMerchandise();
		p_info3.setPaymentType(PaymentType.CARD);
		p_info3.setPaymentInfoId(PaymentInfo.getNextId());
		p_info3.setCardNumber("1234567896782345");
		p_info3.setSecurityCode(321);
		p_info3.setExpirationDate(new Date());
		
		payments2.add(p_info3);
		a2.setPaymentInfo(payments2);
		
		accountService.addAccount(a2);
	}
}
