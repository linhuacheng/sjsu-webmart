package com.sjsu.webmart.model.report;

import com.sjsu.webmart.model.auction.AuctionFilter;
import com.sjsu.webmart.model.order.OrderFilter;

public abstract class AccountReport extends Report{

	private AuctionReport auctionReport = new AuctionReport();
	private OrderReport orderReport = new OrderReport();
	
	public void showReport(OrderFilter orderFilter)
	{
		orderReport.showReport(orderFilter); 
		//auctionReport.showReport(auctionFilter);
	}

	public void showReport(AuctionFilter auctionFilter) {
		// TODO Auto-generated method stub
		auctionReport.showReport(auctionFilter);
	}
}
