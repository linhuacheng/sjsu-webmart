package com.sjsu.webmart.service;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.model.auction.AuctionFilter;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.item.Item;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/2/12
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionService {

    public AuctionInfo setupNewAuction(Item item, AuctionType auctionType, float maxBidPrice, Date bidStartTime, Date bidEndTime);

    AuctionResponse closeAuction(int auctionId);

    AuctionResponse closeAuctionByItem(int itemId);

    Bid getWinner(int auctionId);

    AuctionStateType getAuctionState(int auctionId);

    Set<AuctionInfo> getAuctionByState(AuctionStateType auctionStateType);

    void closeAllAuctions();

    void startAllAuctions();

    Collection<AuctionInfo> getAllAuctions();

    AuctionResponse startAuctionByAuctionId(int auctionId);

    AuctionInfo getAuctionByItemId(int itemId);

    AuctionInfo getAuctionByAuctionId(int auctionId);

    AuctionResponse placeBid(int auctionId, Account account, Item item, float bidPrice);

    List<Bid> findWinningBidByAccount(int accountId);

    List<AuctionInfo> findAuctionInfoByReportFilter(AuctionFilter filter);
}
