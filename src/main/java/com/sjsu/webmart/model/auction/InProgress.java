package com.sjsu.webmart.model.auction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class InProgress implements AuctionState {
    Log log = LogFactory.getLog(InProgress.class);
    private AuctionInterface auctionInfo;

    public InProgress(AuctionInterface auctionInfo){
        this.auctionInfo = auctionInfo;
    }

    @Override
    public void startAuction() {
        log.info("Auction InProgress, Cannot start it");
    }

    @Override
    public Bid endAuction() {
        log.info("Auction InProgress, Ending Auction");
        auctionInfo.setAuctionState(new Closed(auctionInfo));
        auctionInfo.setAuctionEndTime(new Date());
        return auctionInfo.getAuctionStrategy().computeWinner(auctionInfo.getBidList());

    }

    @Override
    public AuctionResponse placeBid(List<Bid> bids,Bid bid, Bid currentBid) {
        log.info("Auction Inprogress, accept bid");
        return auctionInfo.getAuctionStrategy().acceptBid(bids, bid, currentBid);
    }
}
