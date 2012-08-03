package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;

import java.util.List;

/**
 * Closed auction strategy.
 * User: ckempaiah
 * Date: 8/2/12
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClosedAuctionStrategy implements AuctionStrategy {
    //TODO: ck, provide implementation
    @Override
    public AuctionResponse acceptBid(List<Bid> bids, Bid bid, Bid currentBid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bid viewMaxBid(List<Bid> bids) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bid computeWinner(List<Bid> bids) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendNotification() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
