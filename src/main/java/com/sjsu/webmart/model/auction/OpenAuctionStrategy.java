package com.sjsu.webmart.model.auction;

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

    private static Comparator<Bid> descSortByBidPrice = new Comparator<Bid>() {
        @Override
        public int compare(Bid o1, Bid o2) {
            if (o1.getBidPrice() > o2.getBidPrice()){
                return  -1;
            } else if (o2.getBidPrice() > o1.getBidPrice()){
                return 1;
            } else {
                return 0;
            }
        }
    };
    @Override
    public AuctionResponse acceptBid(List<Bid> bids, Bid bid, Bid currentBid) {

        if (currentBid == null || bid.getBidPrice() > currentBid.getBidPrice()) {
            System.out.println("accepting bid " + bid);
            bids.add(bid);
            return AuctionResponse.accepted;
        } else {
            System.out.println("Rejecting bid " + bid);
            return AuctionResponse.rejected_invalid_price;
        }

    }

    @Override
    public Bid viewMaxBid(List<Bid> bids) {

        Collections.sort(bids, descSortByBidPrice);
        Bid maxBid = bids.get(0);
        System.out.println("Max Bid" + maxBid);
        return maxBid;
    }

    @Override
    public Bid computeWinner(List<Bid> bids) {
        Collections.sort(bids, descSortByBidPrice);
        for(Bid bid: bids){
            if (!bid.isOfferRejected()){
                return bid;
            }
        }
        return null;
    }

    @Override
    public void sendNotification() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}