package com.sjsu.webmart.model.report;

import java.util.Date;

import com.sjsu.webmart.model.auction.AuctionFilter;
import com.sjsu.webmart.model.order.OrderFilter;

public class MonthlyReport extends AccountReport{

	private AuctionReport auctionReport = new AuctionReport();
	private OrderReport orderReport = new OrderReport();
	private OrderFilter orderFilter = new OrderFilter();
	private AuctionFilter auctionFilter = new AuctionFilter();
	
	public void showReport(OrderFilter orderFilter) //AuctionFilter auctionFilter
	{
		this.orderFilter = orderFilter;
		super.showReport(orderFilter); //auctionReport
		
	}
	
	public void showReport(AuctionFilter auctionFilter){
		this.auctionFilter = auctionFilter;
		super.showReport(auctionFilter);
		
	}
}
