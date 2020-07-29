package com.bank.flow.console;

import java.util.List;
import java.util.Scanner;

import com.bank.dao.DollarsBankDao;
import com.bank.entity.Account;
import com.bank.flow.State;

public class WelcomeCustomerState extends State{
	Scanner input;
	Account acc;

	public WelcomeCustomerState() {
		super("WelcomeCustomerState");
	}

	@Override
	public void start(Object acc) {
		this.acc = (Account) acc;
		printHeaderText();
		input = new Scanner(System.in);
	}

	@Override
	public void run() {
		System.out.println(ConsoleExtras.ANSI_GREEN + "\nEnter Choice (1,2,3,4,5 or 6) :" + ConsoleExtras.ANSI_RESET);
		if(input.hasNextInt()) {
			int temp = input.nextInt();
			input.nextLine();
			switch(temp) {
			case 1:
				this.controller.changeState("DepositState", acc);
				break;
			case 2:
				this.controller.changeState("WithdrawState", acc);
				break;
			case 3:
				this.controller.changeState("TransferState", acc);
				break;
			case 4:
				List<String> recent = acc.getTransactions();
				System.out.println();
				System.out.println("+------------------------+");
				System.out.println("| 5 Recent Transactions: |");
				System.out.println("+------------------------+");
				for(String str : recent)
					System.out.println(str.toString());
				System.out.println("Balance - " + ConsoleExtras.parseAmount(acc.getBalance()) + " as on " + ConsoleExtras.getTime());
				System.out.println(ConsoleExtras.ANSI_YELLOW + "Press Enter To Continue" + ConsoleExtras.ANSI_RESET);
				input.nextLine();
				printHeaderText();
				break;
			case 5:
				System.out.println();
				System.out.println("+-----------------------+");
				System.out.println("| Customer Information: |");
				System.out.println("+-----------------------+");
				System.out.println("Name: " + acc.getName());
				System.out.println("Address: " + acc.getAddress());
				System.out.println("Contact Number: " + acc.getContactNumber());
				System.out.println(ConsoleExtras.ANSI_YELLOW + "Press Enter To Continue" + ConsoleExtras.ANSI_RESET);
				input.nextLine();
				printHeaderText();
				break;
			case 6:
				this.controller.changeState("WelcomeState");
				break;
			default:
				System.out.println(ConsoleExtras.ANSI_RED + "Invalid Choice" + ConsoleExtras.ANSI_RESET);
			}
		}
	}

	@Override
	public void stop() {
		input = null;
		acc = null;
	}
	
	private void printHeaderText() {
		System.out.println();
		System.out.println("+---------------------+");
		System.out.println("| WELCOME Customer!!! |");
		System.out.println("+---------------------+");
		System.out.println("1. Deposite Amount");
		System.out.println("2. Withdraw Amount");
		System.out.println("3. Funds Transfer");
		System.out.println("4. View 5 Recent Transactions");
		System.out.println("5. Display Customer Information");
		System.out.println("6. Sign Out");
	}
}
