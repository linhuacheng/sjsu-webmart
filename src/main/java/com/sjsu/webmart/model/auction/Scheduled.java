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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void endAuction() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void closeAuction() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void placeBid() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
