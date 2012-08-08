package com.sjsu.webmart.model.report;

import com.sjsu.webmart.model.account.Account;

public abstract class Report {

	private int reportId;
	
	private ReportType reportType;
	
	private Account account;
	
	String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void generateSubject()
	{
		System.out.println(subject);
	}
	
	public void generateContent()
	{
		System.out.println("Content of the Report: ");
	}
	
	public void showReport()
	{
		
	}
	
}
