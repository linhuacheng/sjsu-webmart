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
                    //printAuction(null);
                    break;
                case OPTION_TWO:
                    //setup auction
                    printEnteredOption(out, auctionOptions, option);
                    handleSetupAuction();
                    break;
                case OPTION_THREE:
                    //Start Auction
                    printEnteredOption(out, auctionOptions, option);
                    handleStartAuction();
                    break;
                case OPTION_FOUR:
                    //place bid
                    printEnteredOption(out, auctionOptions, option);
                    handlePlaceBid();
                    break;
                case OPTION_FIVE:
                    //close Auction
                    printEnteredOption(out, auctionOptions, option);
                    handleCloseAuction();
                    break;
                case OPTION_SIX:
                    //List Winner Auction
                    printEnteredOption(out, auctionOptions, option);
                    handlePrintWinner();
                    break;
                case OPTION_EXIT:
                    printText(out, "Enter -1 to return to main menu", false);
                    //printEnteredOption(auctionOptions, secondOption);
                    return;
                default:
                    printText(out, "Invalid Option");
                    option = OptionNum.OPTION_NONE;
                    break;
            }
        }
    }

//    private boolean printAuction(AuctionStateType stateType) {
//        Collection<AuctionInfo> auctionInfos;
//        if (stateType == null) {
//            auctionInfos = auctionService.getAllAuctions();
//        } else {
//            auctionInfos = auctionService.getAuctionByState(stateType);
//        }
//        if (CollectionUtils.isNotEmpty(auctionInfos)) {
//
//            for (AuctionInfo auctionInfo : auctionInfos) {
//                printText(out, String.format("Auction Details Auction Id=(%s) Item Title=(%s), Auction State=(%s)" +
//                        " Start Bid Price=(%s), Bid End Time=(%s), Bid List=(%s)"
//                        , auctionInfo.getAuctionId()
//                        , auctionInfo.getItem().getItemTitle()
//                        , auctionInfo.getAuctionState().getStateType()
//                        , auctionInfo.getStartBidPrice()
//                        , auctionInfo.getAuctionEndTime()
//                        , auctionInfo.getBidList()
//                ));
//            }
//            return true;
//        }
//        printText(out, String.format("No Auction with Type (%s)", stateType));
//        return false;
//    }

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
        existingAuction = auctionService.getAuctionByItemId(item.getItemId());

        if (existingAuction != null && !existingAuction.getAuctionState().equals(AuctionStateType.closed)) {
            printText(out, "Auction for Item is scheduled/Inprogress");
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

    public void createAuctionOptions() {

        ConsoleOption viewAuction = new ConsoleOption("View Auction", OptionNum.OPTION_ONE, null);
        ConsoleOption scheduleAuction = new ConsoleOption("Setup Auction", OptionNum.OPTION_TWO, null);
        ConsoleOption startAuction = new ConsoleOption("Start Auction", OptionNum.OPTION_THREE, null);
        ConsoleOption placeBid = new ConsoleOption("Place Bid", OptionNum.OPTION_FOUR, null);
        ConsoleOption closeAuction = new ConsoleOption("Close Auction", OptionNum.OPTION_FIVE, null);
        ConsoleOption printWinner = new ConsoleOption("List Winner", OptionNum.OPTION_SIX, null);
        auctionOptions = new ArrayList<ConsoleOption>();
        auctionOptions.add(viewAuction);
        auctionOptions.add(scheduleAuction);
        auctionOptions.add(startAuction);
        auctionOptions.add(placeBid);
        auctionOptions.add(closeAuction);
        auctionOptions.add(printWinner);
    }


}
