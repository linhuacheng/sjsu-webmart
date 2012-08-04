package com.sjsu.webmart.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.model.item.Item;
import com.sjsu.webmart.model.notification.MessageObservable;
import com.sjsu.webmart.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
	private static NotificationServiceImpl instance = null;
	
	
	private NotificationServiceImpl(){
		
	}
	
	public static NotificationServiceImpl getInstance() {
		if (instance == null) {
		synchronized (NotificationServiceImpl.class){
		if (instance == null) {
		instance = new NotificationServiceImpl();
		}
		}
		}
		return instance;
		}

	@Override
	public void update(MessageObservable message, Object args) {
		System.out.println("MESSAGE NOTIFICATION:" + args);
		
	}
	
}
