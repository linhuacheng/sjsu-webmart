package com.sjsu.webmart.common;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/2/12
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public enum AuctionType {
    open(1), closed(2);

    private int value;

    private AuctionType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static AuctionType getFromValue(int value){
        for (AuctionType at : AuctionType.values()){
            if (at.value == value){
                return at;
            }
        }
        return null;
    }
}
