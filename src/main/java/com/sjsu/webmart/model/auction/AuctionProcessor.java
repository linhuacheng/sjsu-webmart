package com.sjsu.webmart.model.auction;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionProcessor {

    public void checkBid();

    public void viewMaxBid();

    public void sentNotification();
}
