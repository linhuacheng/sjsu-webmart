package com.sjsu.webmart.model.notification;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.account.Account;

public abstract class EventMessage {

	protected Account account;
	protected String message;

	private List<Notification> notifications = new ArrayList<Notification>();

	public void addObserver(Notification notification) {
		notifications.add(notification);
	}

	public void deleteObserver(Notification notification) {
		notifications.remove(notification);
	}

	public void notifyObservers(Object args) {
		for (Notification notification : notifications) {
			notification.update(this, args);
		}
	}

}
