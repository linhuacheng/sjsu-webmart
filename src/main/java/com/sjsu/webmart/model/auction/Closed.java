package com.sjsu.webmart.model.auction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Closed implements AuctionState {
    private AuctionInterface auctionInfo;

    public Closed(AuctionInterface auctionInfo){
        this.auctionInfo = auctionInfo;
    }
    @Override
    public void startAuction() {
        System.out.println("Auction Closed, Cannot start auction");
    }

    @Override
    public Bid endAuction() {
        System.out.println("Auction closed");
        return auctionInfo.getAuctionStrategy().computeWinner(auctionInfo.getBidList());
    }

    @Override
    public AuctionResponse placeBid(List<Bid> bids,Bid bid, Bid currentBid){
        System.out.println("Auction closed, cannot place bid");
        return AuctionResponse.rejected_auction_closed;
    }
}
