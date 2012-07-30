package com.sjsu.webmart.model.auction;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Closed implements AuctionState {
    private AbstractAuctionInfo auctionInfo;

    public Closed(AbstractAuctionInfo auctionInfo){
        this.auctionInfo = auctionInfo;
    }
    @Override
    public void startAuction() {
        System.out.println("Auction Closed, Cannot start auction");
    }

    @Override
    public void endAuction() {
        System.out.println("Auction closed");
    }

    @Override
    public void placeBid() {
        System.out.println("Auction closed, cannot place bid");
    }
}
