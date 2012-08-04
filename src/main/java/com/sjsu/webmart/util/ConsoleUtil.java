package com.sjsu.webmart.util;

import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

public class ConsoleUtil {

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
    /**
     * prints options
     * @param optionNum
     * @param options
     */
     public static void printOptions(PrintWriter out, OptionNum optionNum, List<ConsoleOption> options){
        if (OptionNum.OPTION_NONE.equals(optionNum)) {
            out.println("Available Options");
            out.println(StringUtils.join(options, "\n"));
        }
        out.print("Enter Option:");
        out.flush();
    }
}
