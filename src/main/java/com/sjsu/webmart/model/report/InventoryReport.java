package com.sjsu.webmart.model.report;

import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class InventoryReport extends Report{

	private AccountService accountService = AccountServiceImpl.getInstance();
	private InventoryService inventoryService = InventoryServiceImpl.getInstance();
	
	public void showReport(int accountId)
	{
		if(accountService.isSeller(accountId))
		{
			generateContent();
		}
		else
			System.out.println("You are not authorized to view inventory report");
	}
	
	@Override
	public void generateSubject() {
		// TODO Auto-generated method stub
		setSubject("INVENTORY REPORT");
		super.generateSubject();
	}

	@Override
	public void generateContent() {
		// TODO Auto-generated method stub
		
		inventoryService.viewAllItems();
	}

}
