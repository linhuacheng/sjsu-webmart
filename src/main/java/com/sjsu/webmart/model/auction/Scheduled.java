package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * Scheduled auction state
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Scheduled implements AuctionState {
    Log log = LogFactory.getLog(Scheduled.class);
    private AuctionInterface auctionInfo;

    public Scheduled(AuctionInterface auctionInfo){
        this.auctionInfo = auctionInfo;
    }

    @Override
    public AuctionResponse startAuction() {
        if (auctionInfo.getAuctionStartTime().before(new Date())) {
            auctionInfo.setAuctionState(new InProgress(auctionInfo));
            log.info("Auction Inprogress");
            return AuctionResponse.success;
        } else {
            log.info("Start time has not reached");
            return AuctionResponse.time_not_reached;
        }

    }

    @Override
    public Bid endAuction() {
        log.info("Auction Scheduled, cannot perform end");
        return null;
    }


    @Override
    public AuctionResponse placeBid(Bid bid) {
        log.info("Auction Scheduled, Cannot place bid");
        return AuctionResponse.rejected_auction_not_scheduled;
    }

    @Override
    public AuctionStateType getStateType() {
        return AuctionStateType.scheduled;
    }
}
