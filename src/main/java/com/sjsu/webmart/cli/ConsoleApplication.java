package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
                    break;
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
}
