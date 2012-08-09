package com.sjsu.webmart.model.item;

import java.util.Date;

public abstract class Item {

	private int itemId;
	
	private String itemTitle;
	
	//private String itemType;
	
	private String itemDescription;
	
	private String sellerName;
	
	private float price;
	
	private int quantity;
	
//	private float rentalPrice;
//	
//	private float buyNowPrice;
	
	//private float maxBidPrice;
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	private float discount;
	
	private int rating;
	
	
	
	//private Date bidStartTime;
	
	//private Date bidEndTime;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

//	public String getItemType() {
//		return itemType;
//	}
//
//	public void setItemType(String itemType) {
//		this.itemType = itemType;
//	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/*public float getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(float rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public float getBuyNowPrice() {
		return buyNowPrice;
	}

	public void setBuyNowPrice(float buyNowPrice) {
		this.buyNowPrice = buyNowPrice;
	}*/

	/*public float getStartBidPrice() {
		return maxBidPrice;
	}

	public void setStartBidPrice(float maxBidPrice) {
		this.maxBidPrice = maxBidPrice;
	}*/

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	/*public Date getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(Date bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public Date getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}*/
	
	
}
