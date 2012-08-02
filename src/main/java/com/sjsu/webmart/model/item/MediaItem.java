package com.sjsu.webmart.model.item;

public class MediaItem extends Item{

	private int duration;
	private String quality;
	private String size;
	
	public MediaItem(int dur, String quality, String size){
		setDuration(dur);
		this.setQuality(quality);
		this.setSize(size);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	// declaration
	
	
	// logic 
}
