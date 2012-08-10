package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;

import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionStrategy {

    public AuctionResponse acceptBid(List<Bid> bids,Bid bid, Bid activeBid, float minBidPrice);

    public Bid getMaxBid(List<Bid> bids);

    public Bid computeWinner(List<Bid> bids);

    public void sendNotification(List<Bid> bids, AuctionInterface auctionInfo);

    public static Comparator<Bid> descSortByBidPrice = new Comparator<Bid>() {
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
}
