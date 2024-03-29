package com.sjsu.webmart.model.report;

import static com.sjsu.webmart.util.ConsoleUtil.NF;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.auction.AuctionFilter;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;

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
		String format = "|%1$-15s|%2$-50s|%3$-50s|%4$-50s|\n";
		
		System.out
				.println("_________________________________________________________________________________________________________________________________________");
		System.out.format(format, "AUCTION ID", "ITEM", "WINNER", "PRICE");
		System.out
				.println("_________________________________________________________________________________________________________________________________________");
		for (AuctionInfo auction : auctions) {
			String user_name = auction.getWinner().getBidder().getFirstName()+auction.getWinner().getBidder().getLastName();
			System.out.format(format, auction.getAuctionId(), auction.getItemTitle(), user_name, NF.format(auction.getWinner().getBidPrice()));
		}
		System.out
				.println("_________________________________________________________________________________________________________________________________________");
	}

}
