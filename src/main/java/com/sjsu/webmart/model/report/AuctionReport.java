package com.sjsu.webmart.model.report;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.auction.AuctionFilter;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.OrderServiceImpl;

public class AuctionReport extends Report{

	AuctionService auctionService = AuctionServiceImpl.getInstance();
	List<AuctionInfo> auctions = new ArrayList<AuctionInfo>();
	@Override
	public void generateSubject() {
		// TODO Auto-generated method stub
		setSubject("AUCTION REPORT");
		super.generateSubject();
	}

	public void showReport(AuctionFilter filter)
	{
		super.showReport();
		
		auctions = auctionService.findAuctionInfoByReportFilter(filter);

		generateSubject();
		generateContent();
	}
	
public void generateContent(){
		
		super.generateContent();
		String format = "|%1$-15s|%2$-50s|%3$-70s|%4$-50s|\n";
		System.out
				.println("_________________________________________________________________________________________________________________________________________");
		System.out.format(format, "AUCTION ID", "ITEM", "WINNER", "PRICE");
		System.out
				.println("_________________________________________________________________________________________________________________________________________");
		for (AuctionInfo auction : auctions) {
			System.out.format(format, auction.getAuctionId(), auction.getItemTitle(), auction.getWinner().getBidder(), auction.getWinner().getBidPrice());
		}
		System.out
				.println("_________________________________________________________________________________________________________________________________________");
	}

}
