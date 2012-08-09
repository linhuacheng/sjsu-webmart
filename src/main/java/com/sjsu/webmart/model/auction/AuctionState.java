package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;

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
    public AuctionResponse startAuction();

    /**
     * ends an auction
     */
    public Bid endAuction();

    /**
     * places bid
     */
    public AuctionResponse placeBid(Bid bid);

    public AuctionStateType getStateType();

    public Bid computeWinner();

}
