package com.sjsu.webmart.model.auction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Closed implements AuctionState {
    Log log = LogFactory.getLog(Closed.class);
    private AuctionInterface auctionInfo;

    public Closed(AuctionInterface auctionInfo){
        this.auctionInfo = auctionInfo;
    }
    @Override
    public void startAuction() {
        log.info("Auction Closed, Cannot start auction");
    }

    @Override
    public Bid endAuction() {
        log.info("Auction closed");
        return auctionInfo.getAuctionStrategy().computeWinner(auctionInfo.getBidList());
    }

    @Override
    public AuctionResponse placeBid(List<Bid> bids,Bid bid, Bid currentBid){
        log.info("Auction closed, cannot place bid");
        return AuctionResponse.rejected_auction_closed;
    }
}
