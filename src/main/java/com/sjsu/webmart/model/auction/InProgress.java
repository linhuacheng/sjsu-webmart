package com.sjsu.webmart.model.auction;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/30/12
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class InProgress implements AuctionState {
    private AbstractAuctionInfo auctionInfo;

    public InProgress(AbstractAuctionInfo auctionInfo){
        this.auctionInfo = auctionInfo;
    }

    @Override
    public void startAuction() {
        System.out.print("Auction InProgress, Cannot start it");
    }

    @Override
    public void endAuction() {
        System.out.print("Auction InProgress, Ending Auction");
        auctionInfo.setAuctionState(new Closed(auctionInfo));
    }

//    @Override
//    public void closeAuction() {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }

    @Override
    public void placeBid() {
        System.out.println("Auction Inprogress, accept bid");
        auctionInfo.checkBid();
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
