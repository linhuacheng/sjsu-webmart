package com.sjsu.webmart.model.auction;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Item;

import java.util.Date;

/**
 *model object to represent bid
 * User: ckempaiah
 * Date: 7/29/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bid {

    private int bidId;
    private Date timeOfBid;
    private Account bidder;
    private Item item;
    private float bidPrice;
    boolean offerRejected;


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

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Account getBidder() {
        return bidder;
    }

    public void setBidder(Account bidder) {
        this.bidder = bidder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isOfferRejected() {
        return offerRejected;
    }

    public void setOfferRejected(boolean offerRejected) {
        this.offerRejected = offerRejected;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", timeOfBid=" + timeOfBid +
                ", bidder=" + bidder +
                ", item=" + item +
                ", bidPrice=" + bidPrice +
                ", offerRejected=" + offerRejected +
                '}';
    }
}
