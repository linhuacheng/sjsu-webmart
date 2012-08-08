package com.sjsu.webmart.service.impl;

import java.util.Date;

import com.sjsu.webmart.model.order.OrderFilter;
import com.sjsu.webmart.model.report.AccountReport;
import com.sjsu.webmart.model.report.InventoryReport;
import com.sjsu.webmart.model.report.MonthlyReport;
import com.sjsu.webmart.model.report.YearlyReport;
import com.sjsu.webmart.service.ReportService;

public class ReportServiceImpl implements ReportService {

	private static ReportServiceImpl instance = null;
	private Date dStart = new Date();
	private Date dEnd = new Date();
	
	private ReportServiceImpl() {
		// TODO Auto-generated constructor stub

	}
	
	public static ReportService getInstance() {
	if (instance == null) {
	synchronized (ReportServiceImpl.class){
	if (instance == null) {
	instance = new ReportServiceImpl();
	}}
	}
	return instance;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void generateMonthlyReport(int accountId, int month, int year) {
		// TODO Auto-generated method stub
		OrderFilter orderFilter = new OrderFilter();
		year = year-1900;
		
		dStart.setYear(year);
		dStart.setMonth(month-1);
		dStart.setDate(1);

		dEnd.setDate(1);
		if(month==12)
		{
			dEnd.setMonth(0);
			dEnd.setYear(year+1);
		}
		else
		{
			dEnd.setMonth(month);
			dEnd.setYear(year);
		}

		System.out.println("Start date ... "+dStart);
		System.out.println("End date ... "+dEnd);
		orderFilter.setAccountId(accountId);
		orderFilter.setStart(dStart);
		orderFilter.setEnd(dEnd);
		
		AccountReport a_report = new MonthlyReport();
		a_report.showReport(orderFilter);
		
		
	}

	@Override
	public void generateYearlyReport(int accountId, int year) {
		// TODO Auto-generated method stub
		OrderFilter orderFilter = new OrderFilter();
		dStart.setYear(year);
		dStart.setMonth(0);
		dStart.setDate(1);
		dEnd.setDate(31);
		dEnd.setMonth(11);
		dEnd.setYear(year);
		
		orderFilter.setStart(dStart);
		orderFilter.setEnd(dEnd);
		orderFilter.setAccountId(accountId);
		AccountReport a_report = new YearlyReport();
		a_report.showReport(orderFilter);
	}

	@Override
	public void generateInventoryReport(int accountId) {
		// TODO Auto-generated method stub
		InventoryReport inventoryReport = new InventoryReport();
		inventoryReport.showReport(accountId);
	}

}
