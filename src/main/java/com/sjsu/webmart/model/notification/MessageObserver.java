package com.sjsu.webmart.model.notification;

public interface MessageObserver {

	public void update (MessageObservable message, Object args);
	
}
