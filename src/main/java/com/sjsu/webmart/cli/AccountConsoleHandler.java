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
import com.sjsu.webmart.model.account.Account;
import com.sjsu.webmart.model.payment.PayMerchandise;
import com.sjsu.webmart.model.payment.PaymentInfo;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;
import com.sjsu.webmart.util.ConsoleUtil;

public class AccountConsoleHandler {

	protected AccountServiceImpl accountService;
	private List<ConsoleOption> accountOptions;
	private PrintWriter out;
	private BufferedReader reader;

	public AccountConsoleHandler(PrintWriter out, BufferedReader reader) {
		this.out = out;
		this.reader = reader;
//		itemService.getInstance();
		createAccountOptions();
	}

	/**
	 * handle main options
	 * 
	 * @throws java.io.IOException
	 */
	public void handleAccountOptions() throws IOException {
		AccountService accountService = AccountServiceImpl.getInstance();
		int accountId;
		int i=0;

		OptionNum secondOption = OptionNum.OPTION_NONE;
		while (true) {
			printOptions(out, secondOption, accountOptions);
			secondOption = getOption(reader);
			switch (secondOption) {
			case OPTION_ONE: 
				accountService.registerUser();
				break;
				
			case OPTION_TWO:
				printEnteredOption(out, accountOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				accountService.viewAccount(accountId);
				break;
				
			case OPTION_THREE:
				printEnteredOption(out, accountOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				System.out.println("Do you want to update Password? (Y/N) : ");
				if(getUserInputString().startsWith("y"))
					accountService.editPassowrd(accountId);
				System.out.println("Do you want to update Address Information? (Y/N) : ");
				if(getUserInputString().startsWith("y"))
					accountService.editAddressInfo(accountId);
				System.out.println("Do you want to update Payment Information? (Y/N) : ");
				if(getUserInputString().startsWith("y"))
					accountService.editPaymentInfo(accountId);
				break;

			case OPTION_FOUR:
				printEnteredOption(out, accountOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				accountService.deleteAccount(accountId);
				break;

			case OPTION_FIVE:
				printEnteredOption(out, accountOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				accountService.processSuspendUserAccount(accountId);
				break;
				
			case OPTION_SIX:
				printEnteredOption(out, accountOptions, secondOption);
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				accountService.processEnableUserAccount(accountId);
				break;
				
			case OPTION_SEVEN:
				printEnteredOption(out, accountOptions, secondOption);
				System.out.println("Enter Your Account Id : ");
				accountId = getUserInput();
				handleViewAllAccounts(accountId);
				break;

			case OPTION_EXIT:
				// printEnteredOption(itemOptions, secondOption);
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

	
	public void createAccountOptions() {
		ConsoleOption createAccount = new ConsoleOption("Create Account",
				OptionNum.OPTION_ONE, null);
		ConsoleOption viewAccount = new ConsoleOption("View Account",
				OptionNum.OPTION_TWO, null);
		ConsoleOption updateAccount = new ConsoleOption("Update Account",
				OptionNum.OPTION_THREE, null);
		ConsoleOption deleteAccount = new ConsoleOption("Delete Account",
				OptionNum.OPTION_FOUR, null);
		ConsoleOption suspendAccount = new ConsoleOption("Suspend Account",
				OptionNum.OPTION_FIVE, null);
		ConsoleOption activateAccount = new ConsoleOption("Activate Account",
				OptionNum.OPTION_SIX, null);
		ConsoleOption viewAllAccount = new ConsoleOption("View All Accounts",
				OptionNum.OPTION_SEVEN, null);
		ConsoleOption exit = new ConsoleOption("Return to Main Menu", OptionNum.OPTION_EXIT, null);
		accountOptions = new ArrayList<ConsoleOption>();
		accountOptions.add(createAccount);
		accountOptions.add(viewAccount);
		accountOptions.add(updateAccount);
		accountOptions.add(deleteAccount);
		accountOptions.add(suspendAccount);
		accountOptions.add(activateAccount);
		accountOptions.add(viewAllAccount);
		accountOptions.add(exit);
	}

	public void handleViewAllAccounts(int accountId)
	{
		AccountService accountService = AccountServiceImpl.getInstance();
		List<Account> accounts = new ArrayList<Account>();
		if(accountService.isSeller(accountId))
		{
			accounts = accountService.getAllAccounts();
			String format = "|%1$-10s|%2$-20s|%3$-30s|%4$-20s|%5$-25s|\n";
			System.out
					.println("________________________________________________________________________________________________________________");
			System.out.format(format, "ACCOUNT ID", "ACCOUNT TYPE", "USER NAME", "PAYMENT INFO TYPE", "ACCOUNT STATUS");
			System.out
					.println("________________________________________________________________________________________________________________");
			for (Account account : accounts) {
				String user_name = account.getFirstName()+" "+account.getLastName();
				String payment_type = "";
				for(PaymentInfo p_info : account.getPaymentInfo())
					payment_type = payment_type+p_info.getPaymentType()+" , ";
				
				System.out.format(format, account.getAccountId(), account.getAccountType(),user_name, payment_type, account.getState().toString());
			}
			System.out
					.println("________________________________________________________________________________________________________________");

		}
		
		else
			System.out.println("You are not authorized to view all the accounts ");
	}
	
}
