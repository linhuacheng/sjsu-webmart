import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.auction.*;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * test class to test auction info
 * User: ckempaiah
 * Date: 7/31/12
 * Time: 1:46 AM
 * To change this template use File | Settings | File Templates.
 */

@Test(groups = "unit")
public class TestAuctionInfo {
    Log log = LogFactory.getLog(TestAuctionInfo.class);
    private Item getItem() {
        Item item = new ConsumerItem("2x2x2", "3lbs");
        item.setItemDescription("Best Ipod nano");
        item.setItemTitle("Consumer item, Ipod nano");
        item.setItemId(1);
        item.setSellerName("Seller1");
        return item;
    }

    private Account getAccount() {
        Account account = new Account();
        return account;
    }

    private AuctionInfo getNewAuctionInfo(){
        OpenAuctionStrategy openAuctionStrategy = new OpenAuctionStrategy();
        return new AuctionInfo(openAuctionStrategy, 300f, new Date(System.currentTimeMillis()-5000),
                new Date(System.currentTimeMillis() + 10000), getItem());

    }
    public void testSetupAuction(){
        log.debug("Test Setup Auction");
        AuctionInfo auctionInfo = getNewAuctionInfo();

        Assert.assertNotNull(auctionInfo, "New Auction Info Should not be null");
        Assert.assertTrue(auctionInfo.getMaxBidPrice() > 0, "Max bid price > 0");
        Assert.assertNotNull(auctionInfo.getAuctionState(), "Auction state must not be null");
        Assert.assertNotNull(auctionInfo.getAuctionStrategy(), "Auction strategy must not null");
        Assert.assertNotNull(auctionInfo.getAuctionStartTime(), "Bid start time must not null");
        Assert.assertNotNull(auctionInfo.getAuctionEndTime(), "Bid end time must not be null");
        Assert.assertTrue(auctionInfo.getAuctionEndTime().after(auctionInfo.getAuctionStartTime()), "Bid end time > bid start time");
        Assert.assertTrue(auctionInfo.getAuctionState() instanceof Scheduled, "Auction State must be scheduled");
        Assert.assertNotNull(auctionInfo.getItem(), "Item must not be null");

    }

    public void testAuctionStateTransition(){

        AuctionInfo auctionInfo = getNewAuctionInfo();
        Assert.assertTrue(auctionInfo.getAuctionState() instanceof Scheduled, "Auction State must be scheduled");

        auctionInfo.getAuctionState().startAuction();
        Assert.assertTrue(auctionInfo.getAuctionState() instanceof InProgress, "Auction State must be inprogress");

        auctionInfo.closeAuction();
        Assert.assertTrue(auctionInfo.getAuctionState() instanceof Closed, "Auction State must be closed");
        auctionInfo.closeAuction();
    }

    public void testPlaceBid(){

        AuctionInfo auctionInfo = getNewAuctionInfo();
        auctionInfo.getAuctionState().startAuction();

        Bid bid1 = new Bid();
        bid1.setBidder(getAccount());
        bid1.setBidId(1);
        bid1.setBidPrice(200f);
        bid1.setItem(getItem());
        bid1.setTimeOfBid(new Date());

        AuctionResponse response1 = auctionInfo.processBid(bid1);
        Assert.assertEquals(response1, AuctionResponse.accepted, "Bid must be accepted");

        Bid bid2 = new Bid();
        bid2.setBidder(getAccount());
        bid2.setBidId(2);
        bid2.setBidPrice(206f);
        bid2.setItem(getItem());
        bid2.setTimeOfBid(new Date());

        AuctionResponse response2 = auctionInfo.processBid(bid2);
        Assert.assertEquals(response2, AuctionResponse.accepted, "Bid must be accepted");

        Bid winner = auctionInfo.closeAuction();

        Assert.assertEquals(winner.getBidPrice(), 206f, "Bid Price must be the one with highest price");

    }

    public void testBidRejectionLowerPrice() {

        AuctionInfo auctionInfo = getNewAuctionInfo();
        auctionInfo.getAuctionState().startAuction();
        Bid bid1 = new Bid();
        bid1.setBidder(getAccount());
        bid1.setBidId(1);
        bid1.setBidPrice(200f);
        bid1.setItem(getItem());
        bid1.setTimeOfBid(new Date());

        Assert.assertEquals(auctionInfo.processBid(bid1), AuctionResponse.accepted, "Bid must be accepted");

        Bid bid2 = new Bid();
        bid2.setBidder(getAccount());
        bid2.setBidId(1);
        bid2.setBidPrice(199f);
        bid2.setItem(getItem());
        bid2.setTimeOfBid(new Date());
        Assert.assertEquals(auctionInfo.processBid(bid2), AuctionResponse.rejected_invalid_price, "Bid must be accepted");
    }


    public void testBidRejectionClosedAuction() {

        AuctionInfo auctionInfo = getNewAuctionInfo();
        auctionInfo.getAuctionState().startAuction();
        Bid bid1 = new Bid();
        bid1.setBidder(getAccount());
        bid1.setBidId(1);
        bid1.setBidPrice(200f);
        bid1.setItem(getItem());
        bid1.setTimeOfBid(new Date());

        Assert.assertEquals(auctionInfo.processBid(bid1), AuctionResponse.accepted, "Bid must be accepted");
        auctionInfo.closeAuction();
        Bid bid2 = new Bid();
        bid2.setBidder(getAccount());
        bid2.setBidId(1);
        bid2.setBidPrice(199f);
        bid2.setItem(getItem());
        bid2.setTimeOfBid(new Date());
        Assert.assertEquals(auctionInfo.processBid(bid2), AuctionResponse.rejected_auction_closed, "Bid must be accepted");
    }

    public void testBidRejectionAuctionNotScheduled() {

        AuctionInfo auctionInfo = getNewAuctionInfo();
        //auctionInfo.getAuctionState().startAuction();
        Bid bid1 = new Bid();
        bid1.setBidder(getAccount());
        bid1.setBidId(1);
        bid1.setBidPrice(200f);
        bid1.setItem(getItem());
        bid1.setTimeOfBid(new Date());

        Assert.assertEquals(auctionInfo.processBid(bid1), AuctionResponse.rejected_auction_not_scheduled, "Bid must be accepted");

    }

    public void testWinner() {

        AuctionInfo auctionInfo = getNewAuctionInfo();
        auctionInfo.getAuctionState().startAuction();
        Bid bid1 = new Bid();
        bid1.setBidder(getAccount());
        bid1.setBidId(1);
        bid1.setBidPrice(200f);
        bid1.setItem(getItem());
        bid1.setTimeOfBid(new Date());
        auctionInfo.processBid(bid1);

        Bid bid3 = new Bid();
        bid3.setBidder(getAccount());
        bid3.setBidId(3);
        bid3.setBidPrice(250f);
        bid3.setItem(getItem());
        bid3.setTimeOfBid(new Date());
        auctionInfo.processBid(bid3);

        Bid bid4 = new Bid();
        bid4.setBidder(getAccount());
        bid4.setBidId(4);
        bid4.setBidPrice(300f);
        bid4.setItem(getItem());
        bid4.setTimeOfBid(new Date());
        auctionInfo.processBid(bid4);

        auctionInfo.getAuctionState().endAuction();
        Bid winner = auctionInfo.closeAuction();
        Assert.assertEquals(winner.getBidPrice(), 300f, "Bid with max price must be winner");

    }
}
