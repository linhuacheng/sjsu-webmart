package com.sjsu.webmart.model.item;

public class ItemDecorator extends Item {

	protected Item item;
	
	public ItemDecorator(Item item){
		this.item = item;
	}

    @Override
    public float getPrice() {
        return item.getPrice();
    }

    @Override
    public void setPrice(float price) {
        item.setPrice(price);
    }

    @Override
    public int getQuantity() {
        return item.getQuantity();
    }

    @Override
    public void setQuantity(int quantity) {
        item.setQuantity(quantity);
    }

    @Override
    public int getItemId() {
        return item.getItemId();
    }

    @Override
    public void setItemId(int itemId) {
        item.setItemId(itemId);
    }

    @Override
    public String getItemTitle() {
        return item.getItemTitle();
    }

    @Override
    public void setItemTitle(String itemTitle) {
        item.setItemTitle(itemTitle);
    }

    @Override
    public String getItemDescription() {
        return item.getItemDescription();
    }

    @Override
    public void setItemDescription(String itemDescription) {
        item.setItemDescription(itemDescription);
    }

    @Override
    public String getSellerName() {
        return item.getSellerName();
    }

    @Override
    public void setSellerName(String sellerName) {
        item.setSellerName(sellerName);
    }
}
