package com.sjsu.webmart.model.notification;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.account.Account;

public interface MessageObservable {

	
	public void addObserver(MessageObserver notification);

	public void deleteObserver(MessageObserver notification);

	public void notifyObservers(Object args);

}
