package com.sjsu.webmart.model.auction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.model.item.Item;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * abstract bid information object
 *
 * User: ckempaiah
 * Date: 7/28/12
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuctionInfo implements AuctionInterface{
    Log log = LogFactory.getLog(AuctionInfo.class);

    int auctionId;
    Date auctionStartTime;
    Date auctionEndTime;
    float maxBidPrice;
    List<Bid> bidList;
    private AuctionStrategy auctionStrategy;
    private Bid currentActiveBid;
    private AuctionState auctionState;
    private Item item;

    public AuctionState getAuctionState() {
        return auctionState;
    }

    public void setAuctionState(AuctionState auctionState) {
        this.auctionState = auctionState;
    }
    public AuctionInfo(AuctionType auctionType, float maxBidPrice
            ,Date bidStartTime, Date bidEndTime, Item item){

        setAuctionStrategy(auctionType);
        this.auctionStartTime = bidStartTime;
        this.auctionEndTime = bidEndTime;
        bidList = new ArrayList<Bid>();
        this.item = item;
        this.maxBidPrice = maxBidPrice;
        auctionState = new Scheduled(this);
    }

    @Override
    public AuctionStrategy getAuctionStrategy() {
        return auctionStrategy;
    }



    public void updateAuction(){
        //TODO: add implementation
    }

    public void startAuction(){
        auctionState.startAuction();
    }
    public Bid closeAuction(){
        return auctionState.endAuction();
    }

    public void setAuctionStrategy(AuctionType auctionType){
        if (AuctionType.open.equals(auctionType)){
            auctionStrategy= new OpenAuctionStrategy();
        } else if (AuctionType.closed.equals(auctionType)){
            auctionStrategy = new ClosedAuctionStrategy();
        }
    }

    public AuctionResponse processBid(Bid newBid){

        log.info("Process Bid:" + newBid);
        AuctionResponse response = auctionState.placeBid(newBid);
        if (AuctionResponse.accepted.equals(response)){
            log.info("New Bid accepted");
            currentActiveBid = newBid;
        } else{
            log.info("New Bid Rejected");
        }
        return response;
    }


    public Bid viewMaxBid(){
        return auctionStrategy.viewMaxBid(bidList);
    }

    public void sendNotification(){
        auctionStrategy.sendNotification();
    }

    public Bid getWinner(){
        return auctionStrategy.computeWinner(bidList);

    }

    //getters and setters


    public Bid getCurrentActiveBid() {
        return currentActiveBid;
    }

    public Item getItem() {
        return item;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public Date getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(Date auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public Date getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(Date auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
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
