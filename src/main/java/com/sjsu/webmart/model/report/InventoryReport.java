package com.sjsu.webmart.model.report;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;

public class InventoryReport extends Report{

	private AccountService accountService = AccountServiceImpl.getInstance();
	private InventoryService inventoryService = InventoryServiceImpl.getInstance();
	private List<Item> items = new ArrayList<Item>();
	
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
