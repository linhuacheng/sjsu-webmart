package com.sjsu.webmart.cli;

import static com.sjsu.webmart.util.ConsoleUtil.getOption;
import static com.sjsu.webmart.util.ConsoleUtil.printEnteredOption;
import static com.sjsu.webmart.util.ConsoleUtil.printOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sjsu.webmart.common.ConsoleOption;
import com.sjsu.webmart.common.OptionNum;
import com.sjsu.webmart.service.ReportService;
import com.sjsu.webmart.service.impl.ReportServiceImpl;

public class ReportConsoleHandler {

	protected ReportServiceImpl reportService;
	private List<ConsoleOption> reportOptions;
	private PrintWriter out;
	private BufferedReader reader;

	public ReportConsoleHandler(PrintWriter out, BufferedReader reader) {
		this.out = out;
		this.reader = reader;
//		itemService.getInstance();
		createReportOptions();
	}

	/**
	 * handle main options
	 * 
	 * @throws java.io.IOException
	 */
	public void handleReportOptions() throws IOException {
		ReportService reportService = ReportServiceImpl.getInstance();
		int accountId, month, year;
		int i=0;

		OptionNum secondOption = OptionNum.OPTION_NONE;
		while (true) {
			printOptions(out, secondOption, reportOptions);
			secondOption = getOption(reader);
			switch (secondOption) {
			case OPTION_ONE: 
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				System.out.println("Enter Month (MM): ");
				month = getUserInput();
				System.out.println("Enter Year (YYYY): ");
				year = getUserInput();
				reportService.generateMonthlyReport(accountId, month, year);
				break;
				
			case OPTION_TWO:
				printEnteredOption(out, reportOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				System.out.println("Enter Year (YYYY): ");
				year = getUserInput();
				reportService.generateYearlyReport(accountId, year);
				break;
				
			case OPTION_THREE:
				printEnteredOption(out, reportOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				reportService.generateInventoryReport(accountId);
				break;

			case OPTION_EXIT:
//				 printEnteredOption(reportOptions, secondOption);
				return;
			default:
				out.println("Invalid Option");
				secondOption = OptionNum.OPTION_NONE;
				break;
			}
		}
	}

	public static int getUserInput() throws IOException{
		InputStreamReader istream = new InputStreamReader(System.in) ;
        BufferedReader bufRead = new BufferedReader(istream) ;
		String input = bufRead.readLine();
		int inputInt = Integer.parseInt(input); 
		return inputInt; 
	}

	
	public static String getUserInputString() throws IOException{
		InputStreamReader istream = new InputStreamReader(System.in) ;
        BufferedReader bufRead = new BufferedReader(istream) ;
		String input = bufRead.readLine();
		return input; 
	}

	
	public void createReportOptions() {
		ConsoleOption viewMonthlyReport = new ConsoleOption("View Monthly Report",
				OptionNum.OPTION_ONE, null);
		ConsoleOption viewYearlyReport = new ConsoleOption("View Yearly Report",
				OptionNum.OPTION_TWO, null);
		ConsoleOption viewInventoryReport = new ConsoleOption("View Inventory Report",
				OptionNum.OPTION_THREE, null);
		ConsoleOption exit = new ConsoleOption("Exit", OptionNum.OPTION_EXIT, null);
		reportOptions = new ArrayList<ConsoleOption>();
		reportOptions.add(viewMonthlyReport);
		reportOptions.add(viewYearlyReport);
		reportOptions.add(viewInventoryReport);
		reportOptions.add(exit);
	}


}
