package com.sjsu.webmart.model.auction;

import java.util.Date;
import java.util.List;

import com.sjsu.webmart.processor.AuctionProcessor;

/**
 * abstract bid information object
 *
 * User: ckempaiah
 * Date: 7/28/12
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAuctionInfo {

    int auctionId;
    Date bidStartTime;
    Date bidEndTime;
    float maxBidPrice;
    List<Bid> bidList;
    private AuctionProcessor auctionProcessor;

    public void setupAuction(){
        //TODO: add implementation
    }

    public void updateAuction(){
        //TODO: add implementation

    }
    public void cancelAuction(){
        //TODO: add implementation
    }
    public void getWinner(){
        //TODO: add implementation
    }

    public void processBid(){
        auctionProcessor.checkBid();
    }
    public void viewMaxBid(){
        auctionProcessor.viewMaxBid();
    }

    public void sendNotification(){
        auctionProcessor.sentNotification();
    }

    //getters and setters
    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public Date getBidStartTime() {
        return bidStartTime;
    }

    public void setBidStartTime(Date bidStartTime) {
        this.bidStartTime = bidStartTime;
    }

    public Date getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(Date bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public float getMaxBidPrice() {
        return maxBidPrice;
    }

    public void setMaxBidPrice(float maxBidPrice) {
        this.maxBidPrice = maxBidPrice;
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }
}
