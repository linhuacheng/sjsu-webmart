package com.sjsu.webmart.model.auction;

import java.util.Date;

/**
 * contains properties to filter various data objects
 * User: ckempaiah
 * Date: 8/8/12
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuctionFilter {
    private int accountId;
    private Date startDate;
    private Date endDate;

    public AuctionFilter(int accountId, Date startDate, Date endDate) {
        this.accountId = accountId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
