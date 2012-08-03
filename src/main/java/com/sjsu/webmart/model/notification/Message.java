package com.sjsu.webmart.model.notification;

public class Message {

	private String sendTo;
	private String message;
	
	public Message(String sendTo, String message) {
		this.sendTo = sendTo;
		this.message = message;
	}
	
	public String toString() {
		return "MESSAGE \n" + 
				"  TO: " + sendTo + "\n" +
				"  MESSAGE: " + message;
	}
}
