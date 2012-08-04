import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.auction.OpenAuctionStrategy;
import com.sjsu.webmart.model.auction.Scheduled;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.MediaItem;
import com.sjsu.webmart.model.order.BuyOrder;
import com.sjsu.webmart.model.order.FulfillmentType;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.model.order.OrderType;
import com.sjsu.webmart.model.order.RentOrder;
import com.sjsu.webmart.model.order.ReturnOrder;
import com.sjsu.webmart.model.payment.PayMerchandise;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.service.impl.OrderServiceImpl;

@Test(groups = "unit")
public class TestOrder {

	Log log = LogFactory.getLog(TestOrder.class);

	OrderService orderService = OrderServiceImpl.getInstance();
	InventoryService inventoryService = InventoryServiceImpl.getInstance();

	private static int ITEM_ID = 1;
	
	private Item getItem() {
		Item item = new ConsumerItem("2x2x2", "3lbs");
		item.setItemDescription("Best Ipod nano");
		item.setItemTitle("Consumer item, Ipod nano");
		item.setItemId(ITEM_ID++);
		item.setSellerName("Seller1");
		item.setPrice(20);
		item.setQuantity(5);
		
		inventoryService.addItem(item);
		
		return item;
	}

	private PaymentInfo getPaymentInfo() {
		PaymentInfo paymentInfo = new PayMerchandise();
		paymentInfo.setCardNumber("1234567899012");
		paymentInfo.setChequeNumber("111122223333");
		return paymentInfo;
	}

	private Account getAccount() {
		Account account = new Account();
		account.setEmail("cheng@gmail.com");
		List<PaymentInfo> payment = new ArrayList<PaymentInfo>();
		payment.add(this.getPaymentInfo());
		account.setPaymentInfo(payment);
		return account;
	}

	private Order getNewBuyOrder() {
		Order buyOrder = new BuyOrder();
		buyOrder.setItem(getItem());
		buyOrder.setAccount(getAccount());

		return buyOrder;
	}

	private Order getNewRentOrder() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 5);

		Order rentOrder = new RentOrder();
		rentOrder.setItem(getItem());
		rentOrder.setAccount(getAccount());
		rentOrder.setFromDate(new Date());
		rentOrder.setToDate(cal.getTime());

		return rentOrder;
	}
	
	private Order getNewReturnOrder() {
		Order returnOrder = new ReturnOrder();
		returnOrder.setItem(getItem());
		returnOrder.setAccount(getAccount());

		return returnOrder;
	}

	private OrderParams getNewBuyOrderParams() {
		Order order = getNewBuyOrder();
		OrderParams params = new OrderParams();
		params.setFulfillmentType(FulfillmentType.COURIER);
		params.setOrder(order);
		params.setOrderType(OrderType.BUY);
		params.setPaymentType(PaymentType.CARD);
		params.setPaymentInfo(order.getAccount().getPaymentInfo().get(0));

		return params;
	}

	private OrderParams getNewRentOrderParams() {
		Order order = getNewRentOrder();
		OrderParams params = new OrderParams();
		params.setFulfillmentType(FulfillmentType.STORE);
		params.setOrder(order);
		params.setOrderType(OrderType.RENT);
		params.setPaymentType(PaymentType.CHEQUE);
		params.setPaymentInfo(order.getAccount().getPaymentInfo().get(0));

		return params;
	}
	
	private OrderParams getNewReturnOrderParams() {
		Order order = getNewReturnOrder();
		OrderParams params = new OrderParams();
		params.setFulfillmentType(FulfillmentType.COURIER);
		params.setOrder(order);
		params.setOrderType(OrderType.RETURN);
		params.setPaymentType(PaymentType.CARD);
		params.setPaymentInfo(order.getAccount().getPaymentInfo().get(0));

		return params;
	}

	public void testBuyOrder() {
		log.debug("Test Buy Order");

		OrderParams orderParams = getNewBuyOrderParams();
		Order order = orderParams.getOrder();

		orderService.placeOrder(orderParams);

		Assert.assertNotNull(orderParams, "New Order Param should not be null");
		Assert.assertNotNull(order, "New Order should not be null");

		Assert.assertNull(orderParams.getOrder().getFromDate(),
				"Rent start date must be null");
		Assert.assertNull(orderParams.getOrder().getToDate(),
				"Rent end date must be null");

		Assert.assertNotNull(orderParams.getOrder().getItem(),
				"Item must not be null");
		Assert.assertEquals(order.calculateCost(orderParams),
				new BigDecimal(20),
				"Calculated buy now cost must be 20.");

	}

	public void testRentOrder() {
		log.debug("Test Rent Order");

		OrderParams orderParams = getNewRentOrderParams();
		Order order = orderParams.getOrder();
		orderService.placeOrder(orderParams);

		Assert.assertNotNull(orderParams, "New Order Param should not be null");
		Assert.assertNotNull(order, "New Order should not be null");

		Assert.assertNotNull(orderParams.getOrder().getFromDate(),
				"Rent start date must not be null");
		Assert.assertNotNull(orderParams.getOrder().getToDate(),
				"Rent end date must not be null");

		Assert.assertNotNull(orderParams.getOrder().getItem(),
				"Item must not be null");
		Assert.assertEquals(order.calculateCost(orderParams),
				new BigDecimal(100),				
				"Calculated rental cost must be 100.");
	}
	
	public void testReturnOrder() {
		log.debug("Test Return Order");

		OrderParams orderParams = getNewReturnOrderParams();
		Order order = orderParams.getOrder();
		orderService.placeOrder(orderParams);

		Assert.assertNotNull(orderParams, "New Order Param should not be null");
		Assert.assertNotNull(order, "New Order should not be null");


		Assert.assertNotNull(orderParams.getOrder().getItem(),
				"Item must not be null");
		Assert.assertEquals(order.calculateCost(orderParams),
				new BigDecimal(-20),				
				"Calculated rental cost must be -20.");
	}

}
