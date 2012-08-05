package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.*;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.account.AccountCLI;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
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
 * Created with IntelliJ IDEA.
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

    public AuctionConsoleHandler(PrintWriter out, BufferedReader reader) {
        this.out = out;
        this.reader = reader;
        auctionService = AuctionServiceImpl.getInstance();
        accountService = AccountServiceImpl.getInstance();
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
                    printEnteredOption(out, auctionOptions, secondOption);
                    break;
                case OPTION_THREE:
                    printEnteredOption(out, auctionOptions, secondOption);
                    handleStartAuction();
                    break;
                case OPTION_FOUR:
                    printEnteredOption(out, auctionOptions, secondOption);
                    break;
                case OPTION_FIVE:
                    printEnteredOption(out, auctionOptions, secondOption);
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
                        " Max Bid Price = (%s), Bid List =(%s)", auctionInfo.getItem().getItemId()
                        , auctionInfo.getItem().getItemDescription()
                        , auctionInfo.getAuctionState().getStateType()
                        , auctionInfo.getMaxBidPrice()
                        , auctionInfo.getBidList()
                ));
            }
        }
    }

    private void handleStartAuction() throws IOException {

        int input;
        printText(out, "Select from the Following Auctions to start an auction");
        printAuction(AuctionStateType.scheduled);

        if ((input = getIdValue(reader)) != -1) {

            if (AuctionResponse.success.equals(auctionService.startAuctionByAuctionId(input))) {
                return;
            }
        }
        printText(out, "Invalid Input");
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
