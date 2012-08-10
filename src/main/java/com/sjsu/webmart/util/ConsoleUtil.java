package com.sjsu.webmart.util;

import com.sjsu.webmart.common.AuctionStateType;
import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.item.Item;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUtil {
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
    public static final NumberFormat NF = new DecimalFormat("#0.00");

    /**
     * prints entered options
     *
     * @param options
     * @param optionNum
     */
    public static void printEnteredOption(PrintWriter out, List<ConsoleOption> options, OptionNum optionNum) {

        out.println("Entered" + options.get(optionNum.getOptionNum() - 1));
    }

    /**
     * gets options
     *
     * @return
     * @throws java.io.IOException
     */
    public static OptionNum getOption(BufferedReader reader) throws IOException {
        String option;
        OptionNum optionNum = OptionNum.OPTION_NONE;

        if ((option = reader.readLine()) != null) {
            try {
                optionNum = OptionNum.getFromValue(Integer.parseInt(option));
            } catch (NumberFormatException nfe) {
                //ignore exception
                optionNum = OptionNum.OPTION_NONE;
            }
        }
        return optionNum;
    }

    /**
     * gets input
     *
     * @return
     * @throws IOException
     */
    public static String getInput(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    public static int getIntValue(BufferedReader reader) throws IOException {
        String option;


        if ((option = reader.readLine()) != null) {
            try {
                return Integer.parseInt(option);
            } catch (NumberFormatException nfe) {
                //ignore exception

            }
        }
        return -1;
    }

    public static int getIdValue(BufferedReader reader) throws IOException {
        return getIntValue(reader);
    }

    public static float getFloatValue(BufferedReader reader) throws IOException {
        String option;


        if ((option = reader.readLine()) != null) {
            try {
                return Float.parseFloat(option);
            } catch (NumberFormatException nfe) {
                //ignore exception

            }
        }
        return -1.0f;
    }

    public static Date getDateValue(BufferedReader reader) throws IOException {
        String value;

        value = reader.readLine();
        if (StringUtils.isNotBlank(value)) {
            value = value.trim();
            try {
                return SDF.parse(value);
            } catch (ParseException pe) {
                //ignore exception

            }
        }
        return null;
    }

    /**
     * prints options
     *
     * @param optionNum
     * @param options
     */
    public static void printOptions(PrintWriter out, OptionNum optionNum, List<ConsoleOption> options) {
        //if (OptionNum.OPTION_NONE.equals(optionNum)) {
        out.println("Available Options");
        out.println(StringUtils.join(options, "\n"));
        //}
        out.print("Enter Option:");
        out.flush();
    }

    public static void printText(PrintWriter out, String text) {
        out.println(text);
        out.flush();
    }

    public static void printText(PrintWriter out, String text, boolean newLine) {
        if (newLine) {
            out.println(text);
        } else {
            out.print(text);
        }
        out.flush();
    }

    public static void printItemDetails(PrintWriter out, List<Item> itemList) {
        List<List<String>> dataRows = new ArrayList<List<String>>();
        if (CollectionUtils.isNotEmpty(itemList)) {
            for (Item item : itemList) {
                List<String> dataRow = new ArrayList<String>();
                dataRow.add("" + item.getItemId());
                dataRow.add(item.getItemTitle());
                dataRow.add(item.getClass().getSimpleName());
                dataRow.add(formatFloat(item.getPrice()));
                dataRow.add(item.getItemDescription());
                dataRows.add(dataRow);
            }
        }
        printDataInTableFormat(out
                , new String[]{"%-15s", "%-20.20s", "%-15s", "%-15s", "%-40s"}
                , new String[]{"ITEM ID", "ITEM TITLE", "ITEM TYPE", "ITEM PRICE", "ITEM DESCRIPTION"}, dataRows);
    }

    public static void printDataInTableFormat(PrintWriter out, String[] formats, String[] headers, List<List<String>> dataRows) {

        String combinedFormat = StringUtils.join(formats, " | ");
        String formatHeader = String.format(combinedFormat, headers);
        String line=StringUtils.leftPad("", formatHeader.length(), "_");

        printText(out, line);
        printText(out, formatHeader);
        printText(out, line);
        if (CollectionUtils.isNotEmpty(dataRows)) {
            for (List<String> dataRow : dataRows) {
                printText(out, String.format(combinedFormat, dataRow.toArray()));
            }
        } else {
            printText(out, "No Data");
        }
        printText(out, line);
    }

    public static void printAccountInfo(PrintWriter out, List<Account> accounts) {
        List<List<String>> dataRows = new ArrayList<List<String>>();
        if (CollectionUtils.isNotEmpty(accounts)) {
            for (Account account : accounts) {
                List<String> dataRow = new ArrayList<String>();
                dataRow.add("" + account.getAccountId());
                dataRow.add(account.getFirstName());
                dataRow.add(account.getLastName());
                dataRow.add(account.getAccountType().toString());
                dataRows.add(dataRow);
            }
        }
        ConsoleUtil.printDataInTableFormat(out
                , new String[]{"%-15s", "%-15s", "%-15s", "%-10s"}
                , new String[]{"ACCOUNT ID", "FIRST NAME", "LAST NAME", "ACCOUNT TYPE"}
                , dataRows);
    }

    public static String formatFloat(float value) {
        return String.format("%10.2f", value);
    }

    public static void printAuctionInfo(PrintWriter out, Collection<AuctionInfo> auctionInfoList1) {


        List<List<String>> dataRows = new ArrayList<List<String>>();
        if (CollectionUtils.isNotEmpty(auctionInfoList1)) {
            for (AuctionInfo auctionInfo : auctionInfoList1) {
                List<String> dataRow = new ArrayList<String>();
                dataRow.add("" + auctionInfo.getAuctionId());
                dataRow.add(auctionInfo.getItem().getItemTitle());
                dataRow.add(auctionInfo.getAuctionState().getStateType().name());
                dataRow.add(formatFloat(auctionInfo.getStartBidPrice()));
                dataRow.add(SDF.format(auctionInfo.getAuctionEndTime()));
                dataRow.add("" + auctionInfo.getBidList().size());
                if (AuctionType.open.equals(auctionInfo.getAuctionType())){
                    dataRow.add(auctionInfo.getCurrentActiveBid() != null ? formatFloat(auctionInfo.getCurrentActiveBid().getBidPrice()) : "");
                } else {
                    dataRow.add("<NA>");
                }
//                Bid wonBid;
//                if ((wonBid =auctionInfo.getWinner()) != null) {
//                    dataRow.add(wonBid.getBidder().getName());
//                    dataRow.add(formatFloat(wonBid.getBidPrice()));
//                }else {
//                    dataRow.add("");
//                    dataRow.add("");
//                }
                boolean winnerPresent=false;
                if (AuctionStateType.closed.equals(auctionInfo.getAuctionState().getStateType())) {
                    Bid wonBid;
                    if ((wonBid = auctionInfo.getWinner()) != null) {
                        dataRow.add(wonBid.getBidder().getName());
                        dataRow.add(formatFloat(wonBid.getBidPrice()));
                        winnerPresent = true;
                    }
                }
                if (!winnerPresent){
                    dataRow.add("");
                    dataRow.add("");
                }

                dataRows.add(dataRow);
            }
        }
        printDataInTableFormat(out
                , new String[]{"%-12.12s", "%-20.20s", "%-13.13s", "%-18.18s", "%-18.18s", "%-12.12s", "%-12.12s", "%-20.20s", "%-12s"}
                , new String[]{"AUCTION ID", "ITEM TITLE", "AUCTION STATE", "START BID PRICE", "AUCTION END TIME", "TOTAL BIDS", "CURRENT BID", "WINNER NAME", "WINNING BID"}
                , dataRows);

//        for (AuctionInfo auctionInfo : auctionInfos) {
//            Bid bid = auctionInfo.getWinner();
//            String winningBidInfo = "";
//            if (bid != null) {
//                winningBidInfo = String.format("Winner Account Id=(%s), Bid Price=(%s)", bid.getBidder().getAccountId(), bid.getBidPrice());
//            }
//            printText(out, String.format("Auction Details Auction Id=(%s) Item Title=(%s) " +
//                    " Auction End Time=%s) Total Bids=(%s), %s"
//                    , auctionInfo.getAuctionId()
//                    , auctionInfo.getItem().getItemTitle()
//                    , auctionInfo.getAuctionEndTime()
//                    , auctionInfo.getBidList().size()
//                    , winningBidInfo
//            ));
//        }

    }
}
