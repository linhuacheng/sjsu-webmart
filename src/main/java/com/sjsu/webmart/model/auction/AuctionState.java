package com.sjsu.webmart.model.auction;

/**
 * Auction state model
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionState {
    /**
     * starts the auction
     */
    public void startAuction();

    /**
     * ends an auction
     */
    public void endAuction();

    /**
     * closes an auction
     */
    public void closeAuction();

    /**
     * places bid
     */
    public void placeBid();

}
