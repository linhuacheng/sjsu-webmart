package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.model.notification.Message;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.List;

/**
 * Closed auction strategy.
 * User: ckempaiah
 * Date: 8/2/12
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClosedAuctionStrategy implements AuctionStrategy {
    private static final Log log = LogFactory.getLog(AuctionStrategy.class);

    @Override
    public AuctionResponse acceptBid(List<Bid> bids, Bid bid, Bid currentMaxBid, float minBidPrice) {

        if (bid.getBidPrice() > minBidPrice){
            log.info("Accepting bid: " + bid);
            bids.add(bid);
            return AuctionResponse.accepted;
        } else {
            log.info("Rejecting bid: " + bid);
            return AuctionResponse.rejected_invalid_price;
        }
    }

    @Override
    public Bid getMaxBid(List<Bid> bids) {
        return null;
    }

    @Override
    public Bid computeWinner(List<Bid> bids) {
        Collections.sort(bids, descSortByBidPrice);
        Bid winner = null;
        boolean winnerSet=false;
        for(Bid bid: bids){
            if (!winnerSet){
                winner = bid;
                winnerSet=true;
            } else {
                bid.setOfferRejected(true);
            }
        }
        return winner;
    }

    @Override
    public void sendNotification(List<Bid> bidList, AuctionInterface auctionInfo) {

        log.info("Sending Notification to all Bidders");
        if (CollectionUtils.isNotEmpty(bidList)){
            for (Bid bid: bidList){
                Message message = new Message(bid.getBidder().getEmail(), "Bid:" + bid);
                auctionInfo.notifyObservers(message);
            }
        }
    }
}
