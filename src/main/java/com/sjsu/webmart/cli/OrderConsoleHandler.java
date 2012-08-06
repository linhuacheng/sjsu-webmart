package com.sjsu.webmart.cli;

import static com.sjsu.webmart.util.ConsoleUtil.getIdValue;
import static com.sjsu.webmart.util.ConsoleUtil.getOption;
import static com.sjsu.webmart.util.ConsoleUtil.printEnteredOption;
import static com.sjsu.webmart.util.ConsoleUtil.printOptions;
import static com.sjsu.webmart.util.ConsoleUtil.printText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sjsu.webmart.common.AuctionResponse;
import com.sjsu.webmart.common.AuctionStateType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.auction.AuctionInfo;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.model.order.BuyOrder;
import com.sjsu.webmart.model.order.FulfillmentType;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.OrderParams;
import com.sjsu.webmart.model.order.OrderType;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.model.payment.PaymentType;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.service.impl.OrderServiceImpl;

public class OrderConsoleHandler {
	protected OrderService orderService;
	private AccountService accountService;
	protected InventoryService inventoryService;
	private List<ConsoleOption> orderOptions;
	private PrintWriter out;
	private BufferedReader reader;
	
	private static SimpleDateFormat MONTH_YEAR_FORMAT = new SimpleDateFormat("MM-yyyy");
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

	public OrderConsoleHandler(PrintWriter out, BufferedReader reader) {
		this.out = out;
		this.reader = reader;
		orderService = OrderServiceImpl.getInstance();
		inventoryService = InventoryServiceImpl.getInstance();
		accountService = AccountServiceImpl.getInstance();
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
				String listRentItemformat = "|%1$-15s|%2$-15s|%3$-20s|\n";
				System.out
						.println("_______________________________________________________");
				System.out.format(listRentItemformat, "ITEM ID", "DESCRIPTION",
						"PRICE PER DAY");
				System.out
						.println("_______________________________________________________");

				for (Item item : inventoryService.listItem(ItemType.RENTABLE)) {
					System.out.format(listRentItemformat, item.getItemId(),
							item.getItemDescription(), item.getPrice());
				}
				System.out
						.println("_______________________________________________________");
				break;
			case OPTION_FOUR:
				printEnteredOption(out, orderOptions, secondOption);
				String listBidItemformat = "|%1$-15s|%2$-15s|%3$-20s|%4$-20s|\n";
				System.out
						.println("___________________________________________________________________________");
				System.out.format(listBidItemformat, "ITEM ID", "DESCRIPTION",
						"MIN PRICE", "SELLER");
				System.out
						.println("___________________________________________________________________________");
				for (Item item : inventoryService.listItem(ItemType.BIDABLE)) {
					System.out.format(listBidItemformat, item.getItemId(),
							item.getItemDescription(), item.getPrice(),
							item.getSellerName());
				}
				System.out
						.println("___________________________________________________________________________");

				break;
			case OPTION_FIVE:
				printEnteredOption(out, orderOptions, secondOption);
				String returnItemformat = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
				System.out
						.println("_________________________________________________________________");
				System.out.format(returnItemformat, "ORDER ID", "TYPE", "ITEM",
						"COST");
				System.out
						.println("_________________________________________________________________");
				for (Order order : orderService.listOrder()) {
					System.out.format(returnItemformat, order.getOrderId(),
							order.getOrderType(), order.getItem()
									.getItemDescription());
				}
				System.out
						.println("_________________________________________________________________");
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
		String format = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
		System.out
				.println("_________________________________________________________________");
		System.out.format(format, "ORDER ID", "TYPE", "ITEM", "COST");
		System.out
				.println("_________________________________________________________________");
		for (Order order : orderService.listOrder()) {
			System.out.format(format, order.getOrderId(), order
					.getOrderType(), order.getItem()
					.getItemDescription(), order.getCost());
		}
		System.out
				.println("_________________________________________________________________");
		
		int input;
		Order order = null;
		while (order ==null) {
			printText(out, "Select an Order to View:");
			try {
				input = getIdValue(reader);
				order = orderService.getOrder(input);
				if (order ==null) {
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
		Account account = accountService.getAllAccounts().get(0);		
		
		Item item = promptItem(ItemType.BUYABLE);
		PaymentType paymentType = this.promptPaymentType();
		PaymentInfo paymentInfo = promptPaymentDetails(account);
		FulfillmentType fulfillmentType = promptShippingType();
		
		Order order = new BuyOrder();
		order.setAccount(account);
		order.setItem(item);
		order.setOrderDate(new Date());
		order.setOrderType(OrderType.BUY);
		
		OrderParams orderParams = new OrderParams();
		orderParams.setFulfillmentType(fulfillmentType);
		orderParams.setPaymentInfo(paymentInfo);
		orderParams.setOrderType(OrderType.BUY);
		orderParams.setPaymentType(paymentType);
		orderParams.setOrder(order);
		
		orderService.placeOrder(orderParams);
	}
	
	private Item promptItem(ItemType type) {
		int input;
		printText(out, "Available items.");
		printItem(type);
		Item item = null;
		while (item ==null) {
			printText(out, "Select Item:");
			try {
				input = getIdValue(reader);
				item = inventoryService.getItem(input);
				if (item ==null) {
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

			String format = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
			System.out
					.println("_________________________________________________________________");
			System.out.format(format, "ITEM ID", "DESCRIPTION", "PRICE",
					"QUANTITY");
			System.out
					.println("_________________________________________________________________");
			for (Item item : items) {
				System.out.format(format, item.getItemId(),
						item.getItemDescription(), item.getPrice(),
						item.getQuantity());
			}
			System.out
					.println("_________________________________________________________________");

		}
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

	private FulfillmentType promptShippingType() {
		printText(out, "Shipping Type:");
		printText(out, "[1] Courier");
		printText(out, "[2] Online");
		printText(out, "[3] Store Pickup");
		FulfillmentType type = null;
		while (type ==null) {
			printText(out, "Select Shipping Type:");
			try {
				int input = getIdValue(reader);
				type = getFulfillmentType(input);
				if (type ==null) {
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
		while (type ==null) {
			printText(out, "Select Payment Type:");
			try {
				int input = getIdValue(reader);
				type = getPaymentType(input);
				if (type ==null) {
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
		while (payment ==null) {
			printText(out, "Select Payment Detail:");
			try {
				int input = getIdValue(reader);
				payment = getPaymentInfo(account, input);
				if (payment ==null) {
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

		for (PaymentInfo payment: account.getPaymentInfo()) {
			if (payment.getPaymentInfoId().intValue() == paymentId)
				return payment;
		}
		return null;
	}
	
	public void createOrderOptions() {
		ConsoleOption viewOrder = new ConsoleOption("View Order",
				OptionNum.OPTION_ONE, null);
		ConsoleOption buyItem = new ConsoleOption("Buy Item",
				OptionNum.OPTION_TWO, null);
		ConsoleOption rentItem = new ConsoleOption("Rent Item",
				OptionNum.OPTION_THREE, null);
		ConsoleOption bidItem = new ConsoleOption("Bid Item",
				OptionNum.OPTION_THREE, null);
		ConsoleOption returnItem = new ConsoleOption("Return Item",
				OptionNum.OPTION_FIVE, null);
		orderOptions = new ArrayList<ConsoleOption>();
		orderOptions.add(viewOrder);
		orderOptions.add(buyItem);
		orderOptions.add(rentItem);
		orderOptions.add(bidItem);
		orderOptions.add(returnItem);
	}

}
