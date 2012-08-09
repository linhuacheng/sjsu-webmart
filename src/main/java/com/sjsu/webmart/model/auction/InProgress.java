package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * in progress auction state
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
    public AuctionResponse startAuction() {
        log.info("Auction InProgress, Cannot start it");
        return AuctionResponse.invalid_operation;
    }

    @Override
    public Bid endAuction() {
        log.info("Auction InProgress, Ending Auction");
        auctionInfo.setAuctionState(new Closed(auctionInfo));
        auctionInfo.setAuctionEndTime(new Date());
        return auctionInfo.getAuctionStrategy().computeWinner(auctionInfo.getBidList());

    }

    @Override
    public AuctionResponse placeBid(Bid bid) {
        List<Bid> bids = auctionInfo.getBidList();
        Bid currentMaxBid = auctionInfo.getCurrentActiveBid();
        log.info("Auction Inprogress, accept bid");
        float minBidPrice = currentMaxBid != null ? currentMaxBid.getBidPrice() : auctionInfo.getStartBidPrice();
        return auctionInfo.getAuctionStrategy().acceptBid(bids, bid, minBidPrice);
    }

    @Override
    public AuctionStateType getStateType() {
        return AuctionStateType.inprogress;
    }

    @Override
    public Bid computeWinner() {
        log.debug(String.format("Auction is in (%s) state, it must be closed to compute winner", getStateType()));
        return null;
    }
}
