package com.sjsu.webmart.common;

/**
 * auction response enum
 * User: ckempaiah
 * Date: 7/31/12
 * Time: 6:52 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AuctionResponse {
    rejected_invalid_price
    , rejected_auction_closed
    , rejected_auction_not_scheduled
    , accepted
    , auction_not_found
    , invalid_operation
    , success
    , time_not_reached
    , invalid_start_auction_argument
    ;
}
