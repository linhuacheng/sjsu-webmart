package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import com.sjsu.webmart.model.notification.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenAuctionStrategy implements AuctionStrategy {

    Log log = LogFactory.getLog(Scheduled.class);

    @Override
    public AuctionResponse acceptBid(List<Bid> bids, Bid bid,Bid currentMaxBid, float minBidPrice) {

        if (currentMaxBid != null){
            minBidPrice = currentMaxBid.getBidPrice();
        }
        if (bid.getBidPrice() > minBidPrice) {
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

        Collections.sort(bids, descSortByBidPrice);
        Bid maxBid = bids.get(0);
        log.info("Max Bid" + maxBid);
        return maxBid;
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
                bid.setWinner(true);
            } else {
                bid.setWinner(false);
            }
        }
        return winner;
    }

    @Override
    public void sendNotification(List<Bid> bids, AuctionInterface auctionInfo) {

        Bid bid = computeWinner(bids);
        log.info("Sending Notification to Winner");
        if (bid != null){
            Message message = new Message(bid.getBidder().getEmail(), "Bid:" + bid);
            auctionInfo.notifyObservers(message);
        }
    }
}
