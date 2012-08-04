package com.sjsu.webmart.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/3/12
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleOption {
    private String optionName;
    private OptionNum optionNum;
    private List<ConsoleOption> childOptions;

    public ConsoleOption(String name, OptionNum num, List<ConsoleOption> childOptions){
        this.optionName = name;
        this.optionNum = num;
        this.childOptions = childOptions;
    }

    public ConsoleOption addChildOption(ConsoleOption consoleOption){

        if (childOptions == null){
            childOptions = new ArrayList<ConsoleOption>();
        }
        childOptions.add(consoleOption);
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s. %s", optionNum, optionName);
    }


}
