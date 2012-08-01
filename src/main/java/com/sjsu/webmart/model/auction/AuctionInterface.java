package com.sjsu.webmart.model.auction;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
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

}
