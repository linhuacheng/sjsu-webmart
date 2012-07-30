package com.sjsu.webmart.model.auction;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Scheduled implements AuctionState {

    private AbstractAuctionInfo auctionInfo;

    public Scheduled(AbstractAuctionInfo auctionInfo){
        this.auctionInfo = auctionInfo;
    }

    @Override
    public void startAuction() {
        auctionInfo.setAuctionState(new InProgress(auctionInfo));
        System.out.println("Auction Inprogress");
    }

    @Override
    public void endAuction() {
        System.out.println("Auction Scheduled, cannot perform end");
    }


    @Override
    public void placeBid() {
        System.out.println("Auction Scheduled, Cannot place bid");
    }
}
