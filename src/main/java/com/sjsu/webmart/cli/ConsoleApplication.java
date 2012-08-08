package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.Bidable;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.test.ItemData;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.sjsu.webmart.util.ConsoleUtil.*;

/**
 * Console application
 * User: ckempaiah
 * Date: 8/3/12
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleApplication {

    private PrintWriter out;
    private BufferedReader reader;
    private List<ConsoleOption> mainOptions;
    AuctionConsoleHandler auctionConsoleHandler;
    OrderConsoleHandler orderConsoleHandler;
    ItemConsoleHandler itemConsoleHandler;
    AuctionService auctionService= AuctionServiceImpl.getInstance();
    AccountService accountService = AccountServiceImpl.getInstance();
    InventoryService inventoryService = InventoryServiceImpl.getInstance();

    /**
     *
     * @param reader
     * @param out
     */
    public ConsoleApplication(BufferedReader reader, PrintWriter out) {
        this.reader = reader;
        this.out = out;

    }

    /**
     * main method
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        PrintWriter out = new PrintWriter(System.out, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleApplication consoleApplication = new ConsoleApplication(reader, out);

        consoleApplication.createConsoleOptions();
        consoleApplication.auctionConsoleHandler = new AuctionConsoleHandler(out, reader);
        consoleApplication.itemConsoleHandler = new ItemConsoleHandler(out, reader);
        consoleApplication.orderConsoleHandler = new OrderConsoleHandler(out, reader);
        consoleApplication.setupSampleData();
        consoleApplication.start();

    }

    /**
     * presents main options
     * @throws IOException
     */
    private void start() throws IOException {
        OptionNum optionNum = OptionNum.OPTION_NONE;

        while (true) {

            printOptions(out, optionNum, mainOptions);
            optionNum = getOption(reader);

            switch (optionNum) {
                case OPTION_ONE:
                    printEnteredOption(out,mainOptions, optionNum);

                    break;
                case OPTION_TWO:
                    printEnteredOption(out,mainOptions, optionNum);
                    //write itemconsolehandler code from here
                    itemConsoleHandler.handleItemOptions();
                    break;
                case OPTION_THREE:
                    printEnteredOption(out,mainOptions, optionNum);
                    orderConsoleHandler.handleOrderOptions();
                    break;
                case OPTION_FOUR:
                    printEnteredOption(out,mainOptions, optionNum);
                    auctionConsoleHandler.handleAuctionOptions();
                    break;
                case OPTION_FIVE:
                    printEnteredOption(out,mainOptions, optionNum);
                    break;
                case OPTION_EXIT:
                    //printEnteredOption(out,mainOptions, optionNum);
                    return;
                default:
                    out.println("Invalid Option");
                    optionNum = OptionNum.OPTION_NONE;
                    break;
            }
        }
    }


    /**
     * create console options
     */
    private void createConsoleOptions() {

        ConsoleOption manageAccount = new ConsoleOption("Manage Account", OptionNum.OPTION_ONE, null);
        ConsoleOption manageItem = new ConsoleOption("Manage Item", OptionNum.OPTION_TWO, null);
        ConsoleOption manageOrder = new ConsoleOption("Manage Order", OptionNum.OPTION_THREE, null);
        ConsoleOption manageAuction = new ConsoleOption("Manage Auction", OptionNum.OPTION_FOUR, null);
        ConsoleOption manageReport = new ConsoleOption("Manage Report", OptionNum.OPTION_FIVE, null);
        ConsoleOption exit = new ConsoleOption("Exit", OptionNum.OPTION_EXIT, null);
        mainOptions = new ArrayList<ConsoleOption>();
        mainOptions.add(manageAccount);
        mainOptions.add(manageItem);
        mainOptions.add(manageOrder);
        mainOptions.add(manageAuction);
        mainOptions.add(manageReport);
        mainOptions.add(exit);

    }

    private void setupSampleData(){
        Item item;
        //Initialize Item data
        ItemData id = new ItemData();
		id.initializeInventory();
        item = inventoryService.listItem(ItemType.BIDABLE).get(0);
        auctionService.setupNewAuction(item, AuctionType.open, 200
                , new Date(System.currentTimeMillis() - 10000), new Date(System.currentTimeMillis() + 10000));

    }
}
