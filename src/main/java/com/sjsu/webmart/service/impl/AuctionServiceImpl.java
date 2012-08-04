package com.sjsu.webmart.service.impl;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.auction.AuctionInterface;
import com.sjsu.webmart.model.auction.AuctionState;
import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.service.AuctionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * service class for auction
 * User: ckempaiah
 * Date: 8/2/12
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuctionServiceImpl implements AuctionService {
    private Log LOG = LogFactory.getLog(AuctionServiceImpl.class);
    private HashMap<Integer, AuctionInfo> auctionInfoList = new HashMap<Integer, AuctionInfo>();
    private static int nextAuctionId = 1;

    @Override
    public void setupNewAuction(Item item, AuctionType auctionType, float maxBidPrice, Date bidStartTime, Date bidEndTime) {

        synchronized (auctionInfoList) {
            LOG.debug("Setting up new Auction");
            AuctionInfo auctionInfo = new AuctionInfo(auctionType, maxBidPrice, bidStartTime, bidEndTime, item);
            auctionInfo.setAuctionId(nextAuctionId);
            auctionInfoList.put(nextAuctionId, auctionInfo);
            nextAuctionId++;
        }
    }

    @Override
    public AuctionResponse closeAuction(int auctionId) {
        AuctionInfo auctionInfo = auctionInfoList.get(auctionId);
        if (auctionInfo != null) {
            auctionInfo.closeAuction();
            return AuctionResponse.success;
        }
        return AuctionResponse.auction_not_found;
    }

    @Override
    public AuctionResponse closeAuctionByItem(int itemId) {
        for (Map.Entry<Integer,AuctionInfo> auctionInfoEntry: auctionInfoList.entrySet()){
            AuctionInfo auctionInfo = auctionInfoEntry.getValue();
            if(itemId == auctionInfo.getItem().getItemId()){
                auctionInfo.closeAuction();
                return AuctionResponse.success;
            }
        }
        return AuctionResponse.auction_not_found;
    }

    @Override
    public Bid getWinner(int auctionId) {

        AuctionInfo auctionInfo = auctionInfoList.get(auctionId);
        if (auctionInfo != null) {
            auctionInfo.getWinner();
        }
        return null;
    }

    @Override
    public AuctionStateType getAuctionState(int auctionId){
        AuctionInfo auctionInfo = auctionInfoList.get(auctionId);
        if (auctionInfo != null) {
            return auctionInfo.getAuctionState().getStateType();
        }
        return null;
    }

    @Override
    public Set<AuctionInfo> getAuctionByState(AuctionStateType auctionStateType) {
        Set<AuctionInfo> auctionInfoSet = new HashSet<AuctionInfo>();
        for (Map.Entry<Integer,AuctionInfo> auctionInfoEntry: auctionInfoList.entrySet()){
            AuctionInfo auctionInfo = auctionInfoEntry.getValue();
            if(auctionStateType.equals(auctionInfo.getAuctionState().getStateType())){
                auctionInfoSet.add(auctionInfo);
            }
        }
        return auctionInfoSet;
    }

    @Override
    public void closeAllAuctions(){

        for (Map.Entry<Integer,AuctionInfo> auctionInfoEntry: auctionInfoList.entrySet()){
            AuctionInfo auctionInfo = auctionInfoEntry.getValue();
            if(AuctionStateType.inprogress.equals(auctionInfo.getAuctionState().getStateType())
                    ||AuctionStateType.scheduled.equals(auctionInfo.getAuctionState().getStateType())){
                auctionInfo.closeAuction();
            }
        }
    }

    @Override
    public void startAllAuctions(){

        for (Map.Entry<Integer,AuctionInfo> auctionInfoEntry: auctionInfoList.entrySet()){
            AuctionInfo auctionInfo = auctionInfoEntry.getValue();
            if(AuctionStateType.scheduled.equals(auctionInfo.getAuctionState().getStateType())){
                auctionInfo.startAuction();
            }
        }
    }
}
