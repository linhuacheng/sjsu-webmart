package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.common.AuctionType;

import java.util.Date;
import java.util.List;

/**
 * auction interface
 * User: ckempaiah
 * Date: 7/31/12
 * Time: 12:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionInterface {

    public void setAuctionState(AuctionState auctionState);

    public AuctionStrategy getAuctionStrategy();

    public List<Bid> getBidList();

    public void setAuctionEndTime(Date auctionEndTime);

    public Date getAuctionStartTime();

    public Bid getCurrentActiveBid();

    float getStartBidPrice();

    AuctionType getAuctionType();
}
