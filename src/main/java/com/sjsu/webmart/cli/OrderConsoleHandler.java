package com.sjsu.webmart.cli;

import static com.sjsu.webmart.util.ConsoleUtil.SDF;
import static com.sjsu.webmart.util.ConsoleUtil.getDateValue;
import static com.sjsu.webmart.util.ConsoleUtil.getIdValue;
import static com.sjsu.webmart.util.ConsoleUtil.getOption;
import static com.sjsu.webmart.util.ConsoleUtil.printEnteredOption;
import static com.sjsu.webmart.util.ConsoleUtil.printOptions;
import static com.sjsu.webmart.util.ConsoleUtil.printText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.auction.Bid;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.model.order.BuyOrder;
import com.sjsu.webmart.model.order.FulfillmentType;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.model.order.OrderStatus;
import com.sjsu.webmart.model.order.OrderType;
import com.sjsu.webmart.model.order.RentOrder;
import com.sjsu.webmart.model.order.RentPeriod;
import com.sjsu.webmart.model.order.ReturnOrder;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.AuctionService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.service.impl.OrderServiceImpl;
import com.sjsu.webmart.util.ConsoleUtil;

public class OrderConsoleHandler {
	private OrderService orderService;
	private AccountService accountService;
	private InventoryService inventoryService;
	private AuctionService auctionService;
	private List<ConsoleOption> orderOptions;
	private PrintWriter out;
	private BufferedReader reader;

	private static SimpleDateFormat MONTH_YEAR_FORMAT = new SimpleDateFormat(
			"MM/yyyy");

	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm");

	private Account account;

	public OrderConsoleHandler(PrintWriter out, BufferedReader reader) {
		this.out = out;
		this.reader = reader;
		orderService = OrderServiceImpl.getInstance();
		inventoryService = InventoryServiceImpl.getInstance();
		accountService = AccountServiceImpl.getInstance();
		auctionService = AuctionServiceImpl.getInstance();
		createOrderOptions();
	}

	/**
	 * handle main options
	 * 
	 * @throws java.io.IOException
	 */

	/*
	 * Mange Order (Buyer) List Item Place Bid (Buyer) Buy Item Enter Payment
	 * Info Enter Shipping Info Rent Item
	 */
	public void handleOrderOptions() throws IOException {

		OptionNum secondOption = OptionNum.OPTION_NONE;

		account = promptAccount();
		while (true) {
			printOptions(out, secondOption, orderOptions);
			secondOption = getOption(reader);
			switch (secondOption) {
			case OPTION_ONE:
				printEnteredOption(out, orderOptions, secondOption);
				handleViewOrder();
				break;
			case OPTION_TWO:
				printEnteredOption(out, orderOptions, secondOption);
				handleBuyItem();
				break;
			case OPTION_THREE:
				printEnteredOption(out, orderOptions, secondOption);
				handleRentItem();
				break;
			case OPTION_FOUR:
				printEnteredOption(out, orderOptions, secondOption);
				handleBuyAuctionItem();
				break;
			case OPTION_FIVE:
				handleReturnItem();
				break;
			case OPTION_EXIT:
				// printEnteredOption(auctionOptions, secondOption);
				return;
			default:
				out.println("Invalid Option");
				secondOption = OptionNum.OPTION_NONE;
				break;
			}
		}
	}

	private void handleViewOrder() {
		printText(out, "Available Orders.");
		String format = "|%1$-15s|%2$-15s|%3$-35s|%4$-10s|%5$-15s|%6$-15s|\n";
		System.out
				.println("________________________________________________________________________________________________________________");
		System.out.format(format, "ORDER ID", "TYPE", "ITEM", "COST",
				"RENT START", "RENT END");
		System.out
				.println("________________________________________________________________________________________________________________");
		OrderFilter filter = new OrderFilter();
		filter.setAccountId(account.getAccountId());
		for (Order order : orderService.findOrders(filter)) {
			RentPeriod rentPeriod = order.getRentPeriod();
			String start = (rentPeriod == null) ? "" : ConsoleUtil.SDF
					.format(rentPeriod.getBegin());
			String end = (rentPeriod == null) ? "" : ConsoleUtil.SDF
					.format(rentPeriod.getEnd());
			System.out.format(format, order.getOrderId(), order.getOrderType(),
					order.getItem().getItemDescription(), order.getCost(),
					start, end);
		}
		System.out
				.println("________________________________________________________________________________________________________________");

		int input;
		Order order = null;
		while (order == null) {
			printText(out, "Select an Order to View:", false);
			try {
				input = getIdValue(reader);
				if (input == -1)
					break;
				order = orderService.getOrder(input);
				if (order == null) {
					printText(out, "Invalid option, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(order);
	}

	private void handleBuyItem() {
		Item item = promptItem(ItemType.BUYABLE);
		PaymentType paymentType = promptPaymentType();
		PaymentInfo paymentInfo = promptPaymentDetails(account);
		FulfillmentType fulfillmentType = promptShippingType(item);

		Order order = new BuyOrder();
		order.setAccount(account);
		order.setItem(item);
		order.setOrderType(OrderType.BUY);

		OrderParams orderParams = new OrderParams();
		orderParams.setFulfillmentType(fulfillmentType);
		orderParams.setPaymentInfo(paymentInfo);
		orderParams.setOrderType(OrderType.BUY);
		orderParams.setPaymentType(paymentType);
		orderParams.setOrder(order);

		orderService.placeOrder(orderParams);
	}

	private void handleRentItem() throws IOException {
		Item item = promptItem(ItemType.RENTABLE);

		printText(out,
				String.format("Enter Rent Start (%s):", SDF.toPattern()), false);
		Date rentStartTime = getDateValue(reader);
		printText(out, String.format("Enter Rent End (%s):", SDF.toPattern()),
				false);
		Date rentEndTime = getDateValue(reader);

		PaymentType paymentType = promptPaymentType();
		PaymentInfo paymentInfo = promptPaymentDetails(account);
		FulfillmentType fulfillmentType = promptShippingType(item);

		RentPeriod rentPeriod = new RentPeriod(rentStartTime, rentEndTime);
		Order order = new RentOrder();
		order.setAccount(account);
		order.setItem(item);
		order.setOrderType(OrderType.RENT);
		order.setRentPeriod(rentPeriod);

		OrderParams orderParams = new OrderParams();
		orderParams.setFulfillmentType(fulfillmentType);
		orderParams.setPaymentInfo(paymentInfo);
		orderParams.setOrderType(OrderType.RENT);
		orderParams.setPaymentType(paymentType);
		orderParams.setOrder(order);

		orderService.placeOrder(orderParams);
	}

	private void handleBuyAuctionItem() {
		Bid bid = promptBid();

		if (bid == null)
			return;

		Item item = bid.getItem();

		PaymentType paymentType = promptPaymentType();
		PaymentInfo paymentInfo = promptPaymentDetails(account);
		FulfillmentType fulfillmentType = promptShippingType(item);

		Order order = new BuyOrder();
		order.setAccount(account);
		order.setItem(item);
		order.setOrderType(OrderType.BUY);

		OrderParams orderParams = new OrderParams();
		orderParams.setBid(bid);
		orderParams.setFulfillmentType(fulfillmentType);
		orderParams.setPaymentInfo(paymentInfo);
		orderParams.setOrderType(OrderType.BUY);
		orderParams.setPaymentType(paymentType);
		orderParams.setOrder(order);

		orderService.placeOrder(orderParams);
	}

	private void handleReturnItem() {
		OrderFilter filter = new OrderFilter();
		filter.setAccountId(account.getAccountId());
		filter.setOrderType(OrderType.BUY);
		filter.setOrderStatus(OrderStatus.COMPLETED);

		Order oldOrder = promptOrders(filter);

		if (oldOrder == null)
			return;

		Item item = oldOrder.getItem();
		printText(
				out,
				"Returning Item:" + item.getItemId() + "-"
						+ item.getItemTitle());
		PaymentType paymentType = promptPaymentType();
		PaymentInfo paymentInfo = promptPaymentDetails(account);
		FulfillmentType fulfillmentType = promptShippingType(item);

		Order order = new ReturnOrder();
		order.setAccount(account);
		order.setItem(item);
		order.setOrderType(OrderType.RETURN);
		order.setCost(new BigDecimal(-oldOrder.getCost().doubleValue()));

		OrderParams orderParams = new OrderParams();
		orderParams.setFulfillmentType(fulfillmentType);
		orderParams.setPaymentInfo(paymentInfo);
		orderParams.setOrderType(OrderType.RETURN);
		orderParams.setPaymentType(paymentType);
		orderParams.setOrder(order);

		// Set old order to retuned
		oldOrder.setOrderStatus(OrderStatus.RETURNED);
		orderService.placeOrder(orderParams);
	}

	private Order promptOrders(OrderFilter filter) {
		printText(out, "Available Orders.");
		String format = "|%1$-15s|%2$-15s|%3$-40s|%4$-10s|\n";
		System.out
				.println("_____________________________________________________________________________________");
		System.out.format(format, "ORDER ID", "TYPE", "ITEM", "COST");
		System.out
				.println("_____________________________________________________________________________________");
		for (Order order : orderService.findOrders(filter)) {
			System.out.format(format, order.getOrderId(), order.getOrderType(),
					order.getItem().getItemDescription(), order.getCost());
		}
		System.out
				.println("_____________________________________________________________________________________");

		int input;
		Order order = null;
		while (order == null) {
			printText(out, "Type the Order ID to Return:", false);
			try {
				input = getIdValue(reader);
				if (input == -1)
					return null;
				order = orderService.getOrder(input);
				if (order == null) {
					printText(out, "Invalid order ID, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return order;
	}

	private Account promptAccount() {
		int input;
		Account account = null;
		while (account == null) {
			printText(out, "Please input Account ID:", false);
			try {
				input = getIdValue(reader);
				account = accountService.findAccountById(input);
				if (account == null) {
					printText(out, "Invalid Account ID, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		printText(
				out,
				"*** Welcome " + account.getFirstName() + " "
						+ account.getLastName() + "! ***");
		return account;
	}

	private Item promptItem(ItemType type) {
		int input;
		printText(out, "Available items.");
		printItem(type);
		Item item = null;
		while (item == null) {
			printText(out, "Select Item:", false);
			try {
				input = getIdValue(reader);
				item = inventoryService.getItem(input);
				if (item == null) {
					printText(out, "Invalid option, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	private void printItem(ItemType type) {
		Collection<Item> items;

		items = inventoryService.listItem(type);

		if (CollectionUtils.isNotEmpty(items)) {

			String format = "|%1$-15s|%2$-40s|%3$-20s|%4$-10s|\n";
			System.out
					.println("__________________________________________________________________________________________");
			System.out.format(format, "ITEM ID", "DESCRIPTION", "PRICE",
					"QUANTITY");
			System.out
					.println("__________________________________________________________________________________________");
			for (Item item : items) {
				System.out.format(format, item.getItemId(),
						item.getItemDescription(), item.getPrice(),
						item.getQuantity());
			}
			System.out
					.println("__________________________________________________________________________________________");

		}
	}

	private Bid promptBid() {
		Collection<Bid> bids;
		bids = auctionService.findWinningBidByAccount(account.getAccountId());

		printText(out, "Auction items won.");
		if (CollectionUtils.isNotEmpty(bids)) {
			String format = "|%1$-15s|%2$-15s|%3$-20s|%4$-20s|\n";
			System.out
					.println("___________________________________________________________________________");
			System.out.format(format, "BID ID", "DESCRIPTION", "PRICE",
					"BID DATE");
			System.out
					.println("___________________________________________________________________________");
			for (Bid b : bids) {
				System.out.format(format, b.getBidId(), b.getItem()
						.getItemTitle(), b.getBidPrice(), DATE_TIME_FORMAT
						.format(b.getTimeOfBid()));
			}
			System.out
					.println("___________________________________________________________________________");

		}

		int input;
		Bid bid = null;
		while (bid == null) {
			printText(out, "Select Auction Item:", false);
			try {
				input = getIdValue(reader);
				if (input == -1)
					return null;
				for (Bid b : bids) {
					if (b.getBidId() == input) {
						bid = b;
						break;
					}
				}
				if (bid == null) {
					printText(out, "Invalid Bid ID, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bid;
	}

	private void printBidsItem() {
	}

	private String maskNumber(String number) {
		if (number != null && number.length() > 4) {
			return "****" + number.substring(number.length() - 4);
		} else {
			return number;
		}
	}

	private void printPaymentOptions(Account account) {
		if (CollectionUtils.isNotEmpty(account.getPaymentInfo())) {

			String format = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
			System.out
					.println("_________________________________________________________________");
			System.out.format(format, "PAYMENT ID", "CARD NUMBER",
					"CHEQUE NUMBER", "EXPIRATION");
			System.out
					.println("_________________________________________________________________");
			for (PaymentInfo payment : account.getPaymentInfo()) {
				System.out.format(format, payment.getPaymentInfoId(),
						maskNumber(payment.getCardNumber()),
						maskNumber(payment.getChequeNumber()),
						MONTH_YEAR_FORMAT.format(payment.getExpirationDate()));
			}
			System.out
					.println("_________________________________________________________________");

		}
	}

	private FulfillmentType promptShippingType(Item item) {
		List<FulfillmentType> fTypes = inventoryService
				.getShippingOptions(item);
		FulfillmentType[] fTypeArr = new FulfillmentType[10];
		int i = 0;
		printText(out, "Shipping Type:");
		for (FulfillmentType ft : fTypes) {
			fTypeArr[i] = ft;
			printText(out, "[" + (i + 1) + "] " + ft.toString());
			i++;
		}

		FulfillmentType type = null;
		while (type == null) {
			printText(out, "Select Shipping Type:", false);
			try {
				int input = getIdValue(reader);
				try {
					type = fTypeArr[input - 1];
				} catch (Exception e) {
					type = null;
				}
				if (type == null) {
					printText(out, "Invalid option, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return type;
	}

	private FulfillmentType getFulfillmentType(int option) {
		switch (option) {
		case 1:
			return FulfillmentType.COURIER;
		case 2:
			return FulfillmentType.ONLINE;
		case 3:
			return FulfillmentType.STORE;
		default:
			return null;
		}
	}

	private PaymentType getPaymentType(int option) {
		switch (option) {
		case 1:
			return PaymentType.CARD;
		case 2:
			return PaymentType.CHEQUE;
		default:
			return null;
		}
	}

	private PaymentType promptPaymentType() {
		printText(out, "Payment Type:");
		printText(out, "[1] Card");
		printText(out, "[2] Cheque");
		PaymentType type = null;
		while (type == null) {
			printText(out, "Select Payment Type:", false);
			try {
				int input = getIdValue(reader);
				type = getPaymentType(input);
				if (type == null) {
					printText(out, "Invalid option, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return type;
	}

	private PaymentInfo promptPaymentDetails(Account account) {

		printPaymentOptions(account);
		PaymentInfo payment = null;
		while (payment == null) {
			printText(out, "Select Payment Detail:", false);
			try {
				int input = getIdValue(reader);
				payment = getPaymentInfo(account, input);
				if (payment == null) {
					printText(out, "Invalid option, please try again.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return payment;
	}

	private PaymentInfo getPaymentInfo(Account account, int paymentId) {
		if (account.getPaymentInfo() == null)
			return null;

		for (PaymentInfo payment : account.getPaymentInfo()) {
			if (payment.getPaymentInfoId().intValue() == paymentId)
				return payment;
		}
		return null;
	}

	public void createOrderOptions() {
		ConsoleOption viewOrder = new ConsoleOption("View My Orders",
				OptionNum.OPTION_ONE, null);
		ConsoleOption buyItem = new ConsoleOption("Buy Item",
				OptionNum.OPTION_TWO, null);
		ConsoleOption rentItem = new ConsoleOption("Rent Item",
				OptionNum.OPTION_THREE, null);
		ConsoleOption buyauctionItem = new ConsoleOption("Buy Auction Item",
				OptionNum.OPTION_FOUR, null);
		ConsoleOption returnItem = new ConsoleOption("Return Item",
				OptionNum.OPTION_FIVE, null);
		orderOptions = new ArrayList<ConsoleOption>();
		orderOptions.add(viewOrder);
		orderOptions.add(buyItem);
		orderOptions.add(rentItem);
		orderOptions.add(buyauctionItem);
		orderOptions.add(returnItem);
	}

}
