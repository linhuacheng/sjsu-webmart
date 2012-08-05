package com.sjsu.webmart.cli;

import com.sjsu.webmart.common.AuctionType;
import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.item.ConsumerItem;
import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.item.ItemType;
import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.service.InventoryService;
import com.sjsu.webmart.service.OrderService;
import com.sjsu.webmart.service.impl.AuctionServiceImpl;
import com.sjsu.webmart.service.impl.InventoryServiceImpl;
import com.sjsu.webmart.service.impl.OrderServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.sjsu.webmart.util.ConsoleUtil.*;


public class OrderConsoleHandler {
    protected OrderService orderService;
    protected InventoryService inventoryService;
    private List<ConsoleOption> orderOptions;
    private PrintWriter out;
    private BufferedReader reader;


    public OrderConsoleHandler(PrintWriter out, BufferedReader reader){
        this.out = out;
        this.reader = reader;
        orderService= OrderServiceImpl.getInstance();
        inventoryService= InventoryServiceImpl.getInstance();
        createOrderOptions();
    }
    /**
     * handle main options
     *
     * @throws java.io.IOException
     */
    
    /*
     Mange Order	(Buyer)
	List Item
	Place Bid (Buyer)
	Buy Item
		Enter Payment Info
		Enter Shipping Info
	Rent Item
     */
    public void handleOrderOptions() throws IOException {

        OptionNum secondOption=OptionNum.OPTION_NONE;
        while(true){
            printOptions(out, secondOption, orderOptions);
            secondOption = getOption(reader);
            switch (secondOption){
                case OPTION_ONE:
                    printEnteredOption(out, orderOptions, secondOption);
                    String format = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
                    System.out.println("_________________________________________________________________");
                    System.out.format(format, "ORDER ID","TYPE","ITEM","COST");
                    System.out.println("_________________________________________________________________");
                    for (Order order: orderService.listOrder()) {
                        System.out.format(format, order.getOrderId(), order.getOrderType(), order.getItem().getItemDescription());
                    }
                    System.out.println("_________________________________________________________________");
                    break;
                case OPTION_TWO:
                    printEnteredOption(out, orderOptions, secondOption);
                    String listBuyItemformat = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
                    System.out.println("_________________________________________________________________");
                    System.out.format(listBuyItemformat, "ITEM ID","DESCRIPTION","PRICE","QUANTITY");
                    System.out.println("_________________________________________________________________");
                    for (Item item: inventoryService.listItem(ItemType.BUYABLE)) {
                        System.out.format(listBuyItemformat, item.getItemId(), item.getItemDescription(), item.getPrice(), item.getQuantity());
                    }
                    System.out.println("_________________________________________________________________");
                    break;
                case OPTION_THREE:
                    printEnteredOption(out, orderOptions, secondOption);
                    String listRentItemformat = "|%1$-15s|%2$-15s|%3$-20s|\n";
                    System.out.println("_______________________________________________________");
                    System.out.format(listRentItemformat, "ITEM ID","DESCRIPTION","PRICE PER DAY");
                    System.out.println("_______________________________________________________");
                    
                    for (Item item: inventoryService.listItem(ItemType.RENTABLE)) {
                        System.out.format(listRentItemformat, item.getItemId(), item.getItemDescription(), item.getPrice());
                    }
                    System.out.println("_______________________________________________________");
                    break;
                case OPTION_FOUR:
                    printEnteredOption(out, orderOptions, secondOption);
                    String listBidItemformat = "|%1$-15s|%2$-15s|%3$-20s|%4$-20s|\n";
                    System.out.println("___________________________________________________________________________");
                    System.out.format(listBidItemformat, "ITEM ID","DESCRIPTION","MIN PRICE", "SELLER");
                    System.out.println("___________________________________________________________________________");
                    for (Item item: inventoryService.listItem(ItemType.BIDABLE)) {
                        System.out.format(listBidItemformat, item.getItemId(), item.getItemDescription(), item.getPrice(), item.getSellerName());
                    }
                    System.out.println("___________________________________________________________________________");
                    
                    break;
                case OPTION_FIVE:
                    printEnteredOption(out, orderOptions, secondOption);
                    String returnItemformat = "|%1$-15s|%2$-15s|%3$-20s|%4$-10s|\n";
                    System.out.println("_________________________________________________________________");
                    System.out.format(returnItemformat, "ORDER ID","TYPE","ITEM","COST");
                    System.out.println("_________________________________________________________________");
                    for (Order order: orderService.listOrder()) {
                        System.out.format(returnItemformat, order.getOrderId(), order.getOrderType(), order.getItem().getItemDescription());
                    }
                    System.out.println("_________________________________________________________________");
                    break;
                case OPTION_EXIT:
                    //printEnteredOption(auctionOptions, secondOption);
                    return;
                default:
                    out.println("Invalid Option");
                    secondOption = OptionNum.OPTION_NONE;
                    break;
            }
        }
    }

    public void createOrderOptions(){
        ConsoleOption viewOrder = new ConsoleOption("View Order", OptionNum.OPTION_ONE, null);
        ConsoleOption buyItem = new ConsoleOption("Buy Item", OptionNum.OPTION_TWO, null);
        ConsoleOption rentItem = new ConsoleOption("Rent Item", OptionNum.OPTION_THREE, null);
        ConsoleOption bidItem = new ConsoleOption("Bid Item", OptionNum.OPTION_THREE, null);
        ConsoleOption returnItem = new ConsoleOption("Return Item", OptionNum.OPTION_FIVE, null);
        orderOptions = new ArrayList<ConsoleOption>();
        orderOptions.add(viewOrder);
        orderOptions.add(buyItem);
        orderOptions.add(rentItem);
        orderOptions.add(bidItem);
        orderOptions.add(returnItem);
    }

}
