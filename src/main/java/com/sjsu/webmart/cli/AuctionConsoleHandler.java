package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.*;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.AccountType;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.sjsu.webmart.util.ConsoleUtil.*;

/**
 * Auction console handler
 * User: ckempaiah
 * Date: 8/4/12
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuctionConsoleHandler {
    private final Log LOG = LogFactory.getLog(AuctionConsoleHandler.class);
    protected AuctionService auctionService;
    private List<ConsoleOption> auctionOptions;
    private PrintWriter out;
    private BufferedReader reader;
    private AccountService accountService;
    private InventoryService inventoryService;


    public AuctionConsoleHandler(PrintWriter out, BufferedReader reader) {
        this.out = out;
        this.reader = reader;
        auctionService = AuctionServiceImpl.getInstance();
        accountService = AccountServiceImpl.getInstance();
        inventoryService = InventoryServiceImpl.getInstance();
        createAuctionOptions();
    }

    /**
     * handle main options
     *
     * @throws java.io.IOException
     */
    public void handleAuctionOptions() throws IOException {

        OptionNum option = OptionNum.OPTION_NONE;
        while (true) {
            printOptions(out, option, auctionOptions);
            option = getOption(reader);

            switch (option) {
                case OPTION_ONE:
                    //view auction
                    printEnteredOption(out, auctionOptions, option);
                    Collection<AuctionInfo> auctions = auctionService.getAllAuctions();
                    printAuctionInfo(out, auctions);
                    break;
                case OPTION_TWO:
                    //setup auction
                    printEnteredOption(out, auctionOptions, option);
                    handleSetupAuction();
                    break;
                case OPTION_THREE:
                    //update auction
                    printEnteredOption(out, auctionOptions, option);
                    handleUpdateAuction();
                    break;
                case OPTION_FOUR:
                    //Start Auction
                    printEnteredOption(out, auctionOptions, option);
                    handleStartAuction();
                    break;
                case OPTION_FIVE:
                    //place bid
                    printEnteredOption(out, auctionOptions, option);
                    handlePlaceBid();
                    break;
                case OPTION_SIX:
                    //close Auction
                    printEnteredOption(out, auctionOptions, option);
                    handleCloseAuction();
                    break;
                case OPTION_SEVEN:
                    //Auction detail
                    printEnteredOption(out, auctionOptions, option);
                    handleAuctionDetail();
                    break;
                case OPTION_EXIT:
                    printText(out, "Enter -1 , Returning to Main Menu", false);
                    //printEnteredOption(auctionOptions, secondOption);
                    return;
                default:
                    printText(out, "Invalid Option");
                    option = OptionNum.OPTION_NONE;
                    break;
            }
        }
    }


    private void handlePrintWinner() {
        Collection<AuctionInfo> auctionInfos;
        auctionInfos = auctionService.getAuctionByState(AuctionStateType.closed);
        if (CollectionUtils.isEmpty(auctionInfos)) {
            printText(out, "No closed auctions");
            return;
        }

        printAuctionInfo(out, auctionInfos);
    }

    private void handleSetupAuction() throws IOException {

        List<Item> itemList = inventoryService.listItem(ItemType.BIDABLE);
        Item item = null;
        int itemId;
        float price;
        int atIntValue;
        AuctionType auctionType;
        AuctionInfo existingAuction;
        AuctionInfo newAuction;
        Date auctionStartTime;
        Date auctionEndTime;
        if (CollectionUtils.isEmpty(itemList)) {
            printText(out, "No Biddable Items Found, create them first");
            return;
        }
        printItemDetails(out, itemList);
        printText(out, "Enter item Id:", false);


        if ((itemId = getIntValue(reader)) != -1) {
            item = inventoryService.getItem(itemId);
        }
        if (item == null) {
            printText(out, "Invalid Item Id");
            return;
        }
        if (item.getQuantity() <=0) {
            printText(out, "Item Not Available");
            return;
        }
        existingAuction = auctionService.getAuctionByItemId(item.getItemId());

        if (existingAuction != null && !AuctionStateType.closed.equals(existingAuction.getAuctionState().getStateType())) {
            printText(out, "Auction for the Item is scheduled/Inprogress");
            return;
        }
        printText(out, "Enter Starting Bid Price:", false);

        if ((price = getFloatValue(reader)) == -1) {
            printText(out, "Invalid Price");
            return;
        }
        printText(out, String.format("Enter Auction End Date (%s):", SDF.toPattern()), false);
        auctionEndTime = getDateValue(reader);
        auctionStartTime = new Date(System.currentTimeMillis() - 10000);

        if (auctionEndTime == null || auctionStartTime.after(auctionEndTime)) {
            printText(out, "Auction end time must be greater than current time");
            return;
        }
        printText(out, "Enter Auction Type");
        printText(out, String.format("[%s] %s", AuctionType.open.getValue(), AuctionType.open));
        printText(out, String.format("[%s] %s", AuctionType.closed.getValue(), AuctionType.closed));

        atIntValue = getIntValue(reader);
        auctionType = AuctionType.getFromValue(atIntValue);
        if (auctionType == null){
            printText(out, "Invalid Auction Type");
            return;
        }
        newAuction = auctionService.setupNewAuction(item, auctionType, price, auctionStartTime, auctionEndTime);

        printText(out, String.format("Successfully Created Auction (%s)", newAuction.getAuctionId()));

    }

    private void handleUpdateAuction() throws IOException {

        final int auctionId;
        float price;
        AuctionInfo existingAuction;
        Date auctionStartTime;
        Date auctionEndTime;

        Set<AuctionInfo> auctionInfoSet = auctionService.getAuctionByState(AuctionStateType.scheduled);
        printAuctionInfo(out, auctionInfoSet);
        if (CollectionUtils.isEmpty(auctionInfoSet)) {
            return;
        }
        printText(out, "Enter Auction id update:", false);
        auctionId = getIntValue(reader);
        existingAuction = (AuctionInfo)CollectionUtils.find(auctionInfoSet, new Predicate(){
            @Override
            public boolean evaluate(Object o) {
                if (((AuctionInfo)o).getAuctionId() == auctionId){
                    return true;
                }
                return false;
            }
        });

        if (existingAuction != null && !AuctionStateType.scheduled.equals(existingAuction.getAuctionState().getStateType())) {
            printText(out, "Cannot update auction as auction does not exist or it is not in scheduled state");
            return;
        }
        printText(out, "Enter Start Bid Price:", false);

        if ((price = getFloatValue(reader)) == -1) {
            printText(out, "Invalid Price");
            return;
        }
        printText(out, String.format("Enter Auction End Date (%s):", SDF.toPattern()), false);
        auctionEndTime = getDateValue(reader);
        auctionStartTime = new Date(System.currentTimeMillis() - 10000);

        if (auctionEndTime == null || auctionStartTime.after(auctionEndTime)) {
            printText(out, "Auction end time must be greater than current time");
            return;
        }

        existingAuction.setAuctionEndTime(auctionEndTime);
        existingAuction.setAuctionStartTime(auctionStartTime);
        existingAuction.setStartBidPrice(price);
        printText(out, String.format("Successfully Updated Auction (%s)", existingAuction.getAuctionId()));

    }
    private void handleStartAuction() throws IOException {

        int input;
        Set<AuctionInfo> auctionInfoSet = auctionService.getAuctionByState(AuctionStateType.scheduled);
        printAuctionInfo(out, auctionInfoSet);
        if (CollectionUtils.isEmpty(auctionInfoSet)) {
            return;
        }
        printText(out, "Enter Auction id to start:", false);
        if ((input = getIntValue(reader)) != -1) {

            if (AuctionResponse.success.equals(auctionService.startAuctionByAuctionId(input))) {
                return;
            }
        }
        printText(out, "Invalid Auction Id");
    }

    private void handleCloseAuction() throws IOException {

        int input;

        //boolean foundAuction1 = printAuction(AuctionStateType.scheduled);
        //boolean foundAuction2 = printAuction(AuctionStateType.inprogress);
        Set<AuctionInfo> auctionInfoSet = auctionService.getAuctionByState(AuctionStateType.scheduled);
        if (CollectionUtils.isNotEmpty(auctionInfoSet)){
            auctionInfoSet.addAll(auctionService.getAuctionByState(AuctionStateType.inprogress));
        } else {
            auctionInfoSet= auctionService.getAuctionByState(AuctionStateType.inprogress);
        }
        printAuctionInfo(out, auctionInfoSet);
        if (CollectionUtils.isEmpty(auctionInfoSet)) {
            return;
        }
        printText(out, "Enter Auction Id to Close:", false);
        if ((input = getIntValue(reader)) != -1) {
            AuctionResponse response = auctionService.closeAuction(input);
            if (AuctionResponse.success.equals(response)) {
                return;
            }
        }
        printText(out, "Invalid Auction Id");
    }

    private void handlePlaceBid() throws IOException {

        int auctionId;
        AuctionInfo auctionInfo = null;
        float bidPrice;
        final int accountId;
        Set<AuctionInfo> auctionInfos = auctionService.getAuctionByState(AuctionStateType.inprogress);
        //boolean foundAuction = printAuction(AuctionStateType.inprogress);
        printAuctionInfo(out, auctionInfos);

        if (CollectionUtils.isEmpty(auctionInfos)) {
            return;
        }
        printText(out, "Enter Auction id to place bid:", false);
        if ((auctionId = getIntValue(reader)) == -1) {
            printText(out, "Invalid Auction Id");
            return;
        }
        auctionInfo = auctionService.getAuctionByAuctionId(auctionId);

        if (auctionInfo == null || !AuctionStateType.inprogress.equals(auctionInfo.getAuctionState().getStateType())) {
            printText(out, "Invalid Auction Id");
            return;
        }
        List<Account> accountList = accountService.getAccountsByType(AccountType.BUYER);
        if (CollectionUtils.isEmpty(accountList)){
            printText(out, "Require buyer type of account to place bid");
            return;

        }
        printAccountInfo(out, accountList);
        printText(out, "Enter Account Id:", false);

        accountId = getIntValue(reader);

        Account account = (Account)CollectionUtils.find(accountList, new Predicate(){
            @Override
            public boolean evaluate(Object o) {
                if (((Account)o).getAccountId() == accountId){
                    return true;
                }
                return false;
            }
        });

        if (account == null) {
            printText(out, "Invalid account id");
            return;
        }

        printText(out, "Enter Bid Price:", false);

        if ((bidPrice = getFloatValue(reader)) == -1) {
            printText(out, "Invalid Price");
            return;
        }

        AuctionResponse auctionResponse = auctionService.placeBid(auctionId, account, auctionInfo.getItem(), bidPrice);
        printText(out, String.format("Status of the bid (%s)", auctionResponse));

    }


    private void handleAuctionDetail() throws IOException {

        Collection<AuctionInfo> auctionInfoList =auctionService.getAllAuctions();
        if (CollectionUtils.isEmpty(auctionInfoList)){
            printText(out, "No Auction to print");
            return;
        }
        printAuctionInfo(out, auctionInfoList);

        printText(out, "Enter Auction Id:", false);
        int auctionId = getIntValue(reader);
        AuctionInfo auctionInfo = auctionService.getAuctionByAuctionId(auctionId);
        if (auctionInfo == null){
            printText(out, "Invalid Auction Id");
            return;
        }
        printText(out, String.format("%s:%s", "AUCTION ID",auctionInfo.getAuctionId()));
        printText(out, String.format("%s:%s", "ITEM TITLE",auctionInfo.getItem().getItemTitle()));
        printText(out, String.format("%s:%s", "AUCTION STATE",auctionInfo.getAuctionState().getStateType()));
        printText(out, String.format("%s:%s", "START BID PRICE",formatFloat(auctionInfo.getStartBidPrice())));
        printText(out, String.format("%s:%s", "AUCTION START TIME",SDF.format(auctionInfo.getAuctionStartTime())));
        printText(out, String.format("%s:%s", "AUCTION END TIME",SDF.format(auctionInfo.getAuctionEndTime())));
        printText(out, String.format("%s:%s", "AUCTION TYPE",auctionInfo.getAuctionType()));
        if (AuctionType.open.equals(auctionInfo.getAuctionType())){
            printText(out, String.format("%s:%s", "CURRENT BID",auctionInfo.getCurrentActiveBid() != null
                    ? formatFloat(auctionInfo.getCurrentActiveBid().getBidPrice()) : ""));
        } else {
            printText(out, String.format("%s:%s", "CURRENT BID","<NA>"));
        }
        if (AuctionStateType.closed.equals(auctionInfo.getAuctionState().getStateType())) {
            Bid wonBid;
            if ((wonBid = auctionInfo.getWinner()) != null) {
                printText(out, String.format("%s:%s", "WINNER NAME",wonBid.getBidder().getName()));
                printText(out, String.format("%s:%s", "WINNING PRICE",formatFloat(wonBid.getBidPrice())));
            }
        }
        if (CollectionUtils.isNotEmpty(auctionInfo.getBidList())){
            printText(out, String.format("%s:%s", "BID SIZE",auctionInfo.getBidList().size()));
            List<List<String>> dataRows = new ArrayList<List<String>>();
            for (Bid bid: auctionInfo.getBidList()){
                List<String> dataRow = new ArrayList<String>();
                dataRow.add(bid.getItem().getItemTitle());
                dataRow.add(bid.getBidder().getName());
                dataRow.add(formatFloat(bid.getBidPrice()));
                dataRow.add(SDF.format(bid.getTimeOfBid()));
                dataRow.add(bid.isWinner()?"true": "false");
                dataRows.add(dataRow);
            }
            printDataInTableFormat(out
                    , new String[]{ "%-30.30s","%-20.20s", "%-13.13s", "%-18.18s", "%-18.18s"}
                    , new String[]{"ITEM TITLE", "BIDDER NAME", "BID PRICE", "TIME OF BID", "WINNER"},dataRows);
        }else{
            printText(out, String.format("%s:%s", "BID SIZE","<EMPTY>"));
        }

    }


    public void createAuctionOptions() {

        ConsoleOption viewAuction = new ConsoleOption("View Auction", OptionNum.OPTION_ONE, null);
        ConsoleOption scheduleAuction = new ConsoleOption("Setup Auction", OptionNum.OPTION_TWO, null);
        ConsoleOption updateAuction = new ConsoleOption("Update Auction", OptionNum.OPTION_THREE, null);
        ConsoleOption startAuction = new ConsoleOption("Start Auction", OptionNum.OPTION_FOUR, null);
        ConsoleOption placeBid = new ConsoleOption("Place Bid", OptionNum.OPTION_FIVE, null);
        ConsoleOption closeAuction = new ConsoleOption("Close Auction", OptionNum.OPTION_SIX, null);
        ConsoleOption auctionDetail = new ConsoleOption("View Auction Detail", OptionNum.OPTION_SEVEN, null);
        ConsoleOption mainMenu = new ConsoleOption("Return to Main Menu", OptionNum.OPTION_EXIT, null);
        auctionOptions = new ArrayList<ConsoleOption>();
        auctionOptions.add(viewAuction);
        auctionOptions.add(scheduleAuction);
        auctionOptions.add(updateAuction);
        auctionOptions.add(startAuction);
        auctionOptions.add(placeBid);
        auctionOptions.add(closeAuction);
        auctionOptions.add(auctionDetail);
        auctionOptions.add(mainMenu);
    }


}
