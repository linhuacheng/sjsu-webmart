package com.sjsu.webmart.model.auction;

import java.util.Date;

/**
 *model object to represent bid
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bid {

    int bidId;
    Date timeOfBid;
    //TODO: replace with account
    String bidder;

    // TODO: replace with item
    String item;
    float bidPrice;

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public Date getTimeOfBid() {
        return timeOfBid;
    }

    public void setTimeOfBid(Date timeOfBid) {
        this.timeOfBid = timeOfBid;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(float bidPrice) {
        this.bidPrice = bidPrice;
    }
}
