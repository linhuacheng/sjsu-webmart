package com.sjsu.webmart.test;

import java.io.Console;
import java.util.Calendar;
import java.util.Date;

import com.sjsu.webmart.model.order.Order;
import com.sjsu.webmart.model.order.RentOrder;

public class TestConsole {

	public static void main(String[] args) {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_YEAR, 5);
    	
    	Date d1 = new Date();
    	Date d2 = cal.getTime();
		
    	System.out.println(getDaysDiff(d1, d2));
		 
	}
	
	private static int getDaysDiff(Date start, Date end) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(start);
		calendar2.setTime(end);
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return (int)Math.ceil(diffDays);
	}
}
