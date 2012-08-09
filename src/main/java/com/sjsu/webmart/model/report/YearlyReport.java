package com.sjsu.webmart.model.report;

import com.sjsu.webmart.model.auction.AuctionFilter;
import com.sjsu.webmart.model.order.OrderFilter;

public class YearlyReport extends AccountReport{

	
	public void showReport(OrderFilter orderFilter)
	{
		super.showReport(orderFilter);
		//auctionReport.showReport(int accountId, 
	}
	
	public void showReport(AuctionFilter auctionFilter){
		super.showReport(auctionFilter);
		
	}
}
