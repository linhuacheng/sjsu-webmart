package com.sjsu.webmart.common;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/3/12
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public enum OptionNum {
    OPTION_ONE(1)
    , OPTION_TWO(2)
    , OPTION_THREE(3)
    , OPTION_FOUR(4)
    , OPTION_FIVE(5)
    , OPTION_SIX(6)
    , OPTION_SEVEN(7)
    , OPTION_EXIT(-1)
    , OPTION_NONE(0)
    ;
    private int optionNum;

    private OptionNum(int option){
        this.optionNum = option;

    }

    @Override
    public String toString() {
        return ""+optionNum;
    }

    public int getOptionNum(){
        return optionNum;
    }

    public static OptionNum getFromValue(int optionValue){
        for(OptionNum op: OptionNum.values()){
            if(optionValue == op.optionNum){
                return op;
            }
        }
        return OPTION_NONE;
    }
}
