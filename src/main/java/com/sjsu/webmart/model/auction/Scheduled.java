package com.sjsu.webmart.model.auction;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Scheduled implements AuctionState {

    private AuctionInterface auctionInfo;

    public Scheduled(AuctionInterface auctionInfo){
        this.auctionInfo = auctionInfo;
    }

    @Override
    public void startAuction() {
        if (auctionInfo.getAuctionStartTime().before(new Date())) {
            auctionInfo.setAuctionState(new InProgress(auctionInfo));
            System.out.println("Auction Inprogress");
        } else {
            System.out.println("Start time has not reached");
        }
    }

    @Override
    public Bid endAuction() {
        System.out.println("Auction Scheduled, cannot perform end");
        return null;
    }


    @Override
    public AuctionResponse placeBid(List<Bid> bids,Bid bid, Bid currentBid) {
        System.out.println("Auction Scheduled, Cannot place bid");
        return AuctionResponse.rejected_auction_not_scheduled;
    }
}
