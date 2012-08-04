package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    protected AuctionServiceImpl auctionService;
    private List<ConsoleOption> auctionOptions;
    private PrintWriter out;
    private BufferedReader reader;


    public AuctionConsoleHandler(PrintWriter out, BufferedReader reader){
        this.out = out;
        this.reader = reader;
        auctionService= new AuctionServiceImpl();
        createAuctionOptions();
    }
    /**
     * handle main options
     *
     * @throws java.io.IOException
     */
    public void handleAuctionOptions() throws IOException {

        OptionNum secondOption=OptionNum.OPTION_NONE;
        while(true){
            printOptions(out, secondOption, auctionOptions);
            secondOption = getOption(reader);
            switch (secondOption){
                case OPTION_ONE:
                    printEnteredOption(out, auctionOptions, secondOption);
                    Item item = new ConsumerItem();
                    item.setItemId(1);
                    item.setItemDescription("Sample Item");
                    item.setPrice(10);
                    Account account = new Account();
                    account.setAccountId(1);
                    account.setFirstName("First");
                    account.setLastName("Last");
                    auctionService.setupNewAuction(item, AuctionType.open,100f, new Date(System.currentTimeMillis()-10000), new Date(System.currentTimeMillis()+10000));
                    break;
                case OPTION_TWO:
                    printEnteredOption(out, auctionOptions, secondOption);
                    break;
                case OPTION_THREE:
                    printEnteredOption(out, auctionOptions, secondOption);
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

    public void createAuctionOptions(){
        ConsoleOption viewAuction = new ConsoleOption("View Auction", OptionNum.OPTION_ONE, null);
        ConsoleOption scheduleAuction = new ConsoleOption("Schedule Auction", OptionNum.OPTION_TWO, null);
        ConsoleOption modifyAuction = new ConsoleOption("Modify Auction", OptionNum.OPTION_THREE, null);
        ConsoleOption closeAuction = new ConsoleOption("Close Auction", OptionNum.OPTION_THREE, null);
        ConsoleOption placeBid = new ConsoleOption("Place Bid", OptionNum.OPTION_FIVE, null);
        auctionOptions = new ArrayList<ConsoleOption>();
        auctionOptions.add(viewAuction);
        auctionOptions.add(scheduleAuction);
        auctionOptions.add(modifyAuction);
        auctionOptions.add(closeAuction);
        auctionOptions.add(placeBid);
    }

}
