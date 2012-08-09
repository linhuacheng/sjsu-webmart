package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Closed auction state
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
    public AuctionResponse startAuction() {
        log.info("Auction Closed, Cannot start auction");
        return AuctionResponse.invalid_operation;
    }

    @Override
    public Bid endAuction() {
        log.info("Auction closed");
        return auctionInfo.getAuctionStrategy().computeWinner(auctionInfo.getBidList());
    }

    @Override
    public AuctionResponse placeBid(Bid bid){
        log.info("Auction closed, cannot place bid");
        return AuctionResponse.rejected_auction_closed;
    }

    @Override
    public AuctionStateType getStateType() {
        return AuctionStateType.closed;
    }

    public Bid computeWinner() {
        return auctionInfo.getAuctionStrategy().computeWinner(auctionInfo.getBidList());
    }
}
