package com.sjsu.webmart.model.auction;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionState {

    public void startAuction();

    public void endAuction();

    public void closeAuction();

}
