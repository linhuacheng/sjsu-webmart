package com.sjsu.webmart.model.account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.sjsu.webmart.service.AccountService;
import com.sjsu.webmart.service.impl.AccountServiceImpl;

public class AccountCLI {
	
	public static void main(String[] args) throws IOException
	{
		
		AccountService as = AccountServiceImpl.getInstance();
		int accountId = 1;
		boolean choice = true;
		
		while(choice)
		{
			System.out.println("\n---------------------------------------------\n");
			System.out.println("Select an option : ");
			System.out.println("1. Create Account");
			System.out.println("2. View Account");
			System.out.println("3. Modify Account");
			System.out.println("4. Suspend Account");
			System.out.println("5. Enable Account");
			System.out.println("6. Cancel Account");
			System.out.println("7. Go to main menu \n");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			char c = (char) br.read();
			
			switch (c) {
			case '1':
				as.registerUser();
				break;

			case '2':
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				as.viewAccount(accountId);
				break;

			case '3':
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				System.out.println("Do you want to update Address Information? (Y/N) : ");
				if(getUserInputString().startsWith("y"))
					as.editPassowrd(accountId);
				System.out.println("Do you want to update Address Information? (Y/N) : ");
				if(getUserInputString().startsWith("y"))
					as.editAddressInfo(accountId);
				System.out.println("Do you want to update Payment Information? (Y/N) : ");
				if(getUserInputString().startsWith("y"))
					as.editPassowrd(accountId);
				break;
			case '4':
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				as.processSuspendUserAccount(accountId);
				break;
			case '5':
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				as.processEnableUserAccount(accountId);
				break;

			case '6':
				System.out.println("Enter Account Id : ");
				accountId = getUserInput();
				as.deleteAccount(accountId);
				break;

			case '7':
				choice = false;
				break;
				
			default:
				System.out.println("Invalid option");
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

}
