package com.sjsu.webmart.model.auction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionStrategy {

    public AuctionResponse acceptBid(List<Bid> bids,Bid bid, Bid currentBid);

    public Bid viewMaxBid(List<Bid> bids);

    public Bid computeWinner(List<Bid> bids);

    public void sendNotification();

}
