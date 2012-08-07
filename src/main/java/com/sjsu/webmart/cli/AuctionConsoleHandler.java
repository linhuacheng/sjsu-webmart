package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.*;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.AccountCLI;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

        OptionNum secondOption = OptionNum.OPTION_NONE;
        while (true) {
            printOptions(out, secondOption, auctionOptions);
            secondOption = getOption(reader);
            switch (secondOption) {
                case OPTION_ONE:
                    //view auction
                    printEnteredOption(out, auctionOptions, secondOption);

//                    Item item = new ConsumerItem();
//                    item.setItemId(1);
//                    item.setItemDescription("Sample Item");
//                    item.setPrice(10);
//                    Account account = new Account();
//                    account.setAccountId(1);
//                    account.setFirstName("First");
//                    account.setLastName("Last");
                    //auctionService.setupNewAuction(item, AuctionType.open,100f, new Date(System.currentTimeMillis()-10000), new Date(System.currentTimeMillis()+10000));
                    printAuction(null);
                    break;
                case OPTION_TWO:
                    //setup auction
                    printEnteredOption(out, auctionOptions, secondOption);
                    handleSetupAuction();
                    break;
                case OPTION_THREE:
                    //Start Auction
                    printEnteredOption(out, auctionOptions, secondOption);
                    handleStartAuction();
                    break;
                case OPTION_FOUR:
                    //place bid
                    printEnteredOption(out, auctionOptions, secondOption);
                    handlePlaceBid();
                    break;
                case OPTION_FIVE:
                    //close Auction
                    printEnteredOption(out, auctionOptions, secondOption);
                    handleCloseAuction();
                    break;
                case OPTION_EXIT:
                    //printEnteredOption(auctionOptions, secondOption);
                    return;
                default:
                    out.println("Invalid Option");
                    secondOption = OptionNum.OPTION_NONE;
                    break;
            }
        }
    }

    private void printAuction(AuctionStateType stateType) {
        Collection<AuctionInfo> auctionInfos;
        if (stateType == null) {
            auctionInfos = auctionService.getAllAuctions();
        } else {
            auctionInfos = auctionService.getAuctionByState(stateType);
        }
        if (CollectionUtils.isNotEmpty(auctionInfos)) {

            for (AuctionInfo auctionInfo : auctionInfos) {
                printText(out, String.format("Auction Details Item Id =(%s), Item Desc = (%s) Auction State = (%s)" +
                        " Max Bid Price = (%s), Bid End Time= (%s), Bid List =(%s)", auctionInfo.getItem().getItemId()
                        , auctionInfo.getItem().getItemTitle()
                        , auctionInfo.getAuctionState().getStateType()
                        , auctionInfo.getMaxBidPrice()
                        , auctionInfo.getAuctionEndTime()
                        , auctionInfo.getBidList()
                ));
            }
            return;
        }
        printText(out,String.format("No Auction with Type (%s)", stateType));
    }

    private void handleSetupAuction() throws IOException {

        List<Item> itemList = inventoryService.listItem(ItemType.BIDABLE);
        Item item= null;
        int itemId;
        float price;
        AuctionInfo existingAuction;
        AuctionInfo newAuction;
        Date auctionStartTime;
        Date auctionEndTime;
        if (CollectionUtils.isEmpty(itemList)){
            printText(out, "No Biddable Items Found, create them first");
            return;
        }
        printItemDetails(out, itemList);
        printText(out,"Enter item Id:", false);


        if ((itemId = getIntValue(reader)) != -1) {
            item = inventoryService.viewItem(itemId);
        }
        if (item == null){
            printText(out, "Invalid Item Id");
            return;
        }
        existingAuction = auctionService.getAuctionByItemId(item.getItemId());

        if ( existingAuction!= null && ! existingAuction.getAuctionState().equals(AuctionStateType.closed)){
            printText(out, "Auction Form Item is scheduled/Inprogress");
            return;
        }
        printText(out,"Enter Max Bid Price:", false);

        if ((price = getFloatValue(reader)) == -1) {
            printText(out, "Invalid Price");
            return;
        }
        printText(out, String.format("Enter Auction End Date (%s):", SDF.toPattern()), false);
        auctionEndTime = getDateValue(reader);
        auctionStartTime = new Date(System.currentTimeMillis()-10000);

        if (auctionEndTime == null || auctionStartTime.after(auctionEndTime)){
            printText(out, "Auction end time must be greater than current time");
            return;
        }

        newAuction = auctionService.setupNewAuction(item,AuctionType.open,price, auctionStartTime, auctionEndTime);

        printText(out, String.format("Successfully Created Auction (%s)", newAuction.getAuctionId()));

    }
    private void handleStartAuction() throws IOException {

        int input;
        printText(out, "Select from the Following Auctions to start an auction");
        printAuction(AuctionStateType.scheduled);

        if ((input = getIntValue(reader)) != -1) {

            if (AuctionResponse.success.equals(auctionService.startAuctionByAuctionId(input))) {
                return;
            }
        }
        printText(out, "Invalid Auction Id");
    }

    private void handleCloseAuction() throws IOException {

        int input;
        printText(out, "Select from the Following Auctions to close an auction");
        printAuction(AuctionStateType.scheduled);
        printAuction(AuctionStateType.inprogress);

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
        int accountId;
        printText(out, "Select from the Following Auctions to place bid");
        printAuction(AuctionStateType.inprogress);

        if ((auctionId = getIntValue(reader)) == -1) {
            printText(out, "Invalid Auction Id");
            return;
        }
        auctionInfo = auctionService.getAuctionByAuctionId(auctionId);

        if (auctionInfo == null || !AuctionStateType.inprogress.equals(auctionInfo.getAuctionState().getStateType())) {
            printText(out, "Invalid Auction Id");
            return;
        }
        printText(out, "Enter Account Id:", false);

        if ((accountId = getIntValue(reader)) == -1) {
            printText(out, "Invalid Account Id");
            return;
        }

        Account account = accountService.findAccountById(accountId);
        if (account== null){
            printText(out, "Invalid account id");
            return;
        }

        printText(out,"Enter Max Bid Price:", false);

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
        ConsoleOption modifyAuction = new ConsoleOption("Start Auction", OptionNum.OPTION_THREE, null);
        ConsoleOption closeAuction = new ConsoleOption("Place Bid", OptionNum.OPTION_FOUR, null);
        ConsoleOption placeBid = new ConsoleOption("Close Auction", OptionNum.OPTION_FIVE, null);
        auctionOptions = new ArrayList<ConsoleOption>();
        auctionOptions.add(viewAuction);
        auctionOptions.add(scheduleAuction);
        auctionOptions.add(modifyAuction);
        auctionOptions.add(closeAuction);
        auctionOptions.add(placeBid);
    }


}
