package com.sjsu.webmart.util;

import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.item.Item;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConsoleUtil {
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
    public static final NumberFormat NF = new DecimalFormat("#0.00");
    /**
     * prints entered options
     * @param options
     * @param optionNum
     */
    public static void printEnteredOption(PrintWriter out, List<ConsoleOption> options, OptionNum optionNum){

        out.println("Entered" + options.get(optionNum.getOptionNum() - 1));
    }

    /**
     * gets options
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
     * @return
     * @throws IOException
     */
    public static String getInput(BufferedReader reader)throws IOException{
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

    public static int getIdValue(BufferedReader reader) throws IOException{
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
     * @param optionNum
     * @param options
     */
     public static void printOptions(PrintWriter out, OptionNum optionNum, List<ConsoleOption> options){
        //if (OptionNum.OPTION_NONE.equals(optionNum)) {
            out.println("Available Options");
            out.println(StringUtils.join(options, "\n"));
        //}
        out.print("Enter Option:");
        out.flush();
    }

    public static void printText(PrintWriter out, String text){
        out.println(text);
        out.flush();
    }

    public static void printText(PrintWriter out, String text, boolean newLine){
        if (newLine){
            out.println(text);
        } else {
            out.print(text);
        }
        out.flush();
    }
    public static void printItemDetails(PrintWriter out, List<Item> itemList){
        for (Item item: itemList){
            out.println(String.format("Item Id =(%s), Item Price=(%s), Item Type=(%s), Item Title =(%s),Item Description=(%s)"
                    ,item.getItemId()
                    ,item.getPrice()
                    ,item.getClass().getName()
                    ,item.getItemTitle()
                    ,item.getItemDescription()
                    ));
        }
    }
}
