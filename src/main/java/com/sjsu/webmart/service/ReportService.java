package com.sjsu.webmart.service;

public interface ReportService {

	public void generateMonthlyReport(int accountId, int month, int year);
	
	public void generateYearlyReport(int accountId, int year);
	
	public void generateInventoryReport(int accountId);
	
}
