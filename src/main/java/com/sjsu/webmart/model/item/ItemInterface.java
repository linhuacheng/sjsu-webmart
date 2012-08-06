package com.sjsu.webmart.model.item;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/6/12
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ItemInterface {

    public float getPrice();

    public void setPrice(float price);

    public int getQuantity();

    public void setQuantity(int quantity);

    public int getItemId();

    public void setItemId(int itemId);

    public String getItemTitle();

    public void setItemTitle(String itemTitle);

    public String getItemDescription();

    public void setItemDescription(String itemDescription);

    public String getSellerName();

    public void setSellerName(String sellerName);
}
