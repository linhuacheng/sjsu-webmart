package com.sjsu.webmart.model.item;

public class MediaItem extends Item{

	private String duration;
	private String quality;
	private String size;
	
	public MediaItem(String dur, String quality, String size){
		setDuration(dur);
		this.setQuality(quality);
		this.setSize(size);
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String dur) {
		this.duration = dur;
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
