package com.bank.flow.console;

import java.util.Scanner;

import com.bank.dao.DollarsBankDao;
import com.bank.entity.Account;
import com.bank.flow.State;

public class LoginState extends State{
	Scanner input;
	String id;
	String password;
	int subState;

	public LoginState() {
		super("LoginState");
	}

	@Override
	public void start(Object obj) {
		System.out.println();
		System.out.println("+---------------------+");
		System.out.println("| Enter Login Details |");
		System.out.println("+---------------------+");
		input = new Scanner(System.in);
		id = null;
		password = null;
		subState = 0;
	}

	@Override
	public void run() {
		switch(subState) {
			case 0:
				System.out.println("User Id:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNextLine()) {
					id = input.nextLine();
					++subState;
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 1:
				System.out.println("Password:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNextLine()) {
					password = input.nextLine();
					Account acc = DollarsBankDao.getAccount(id, password);
					if(acc != null)
						this.controller.changeState("WelcomeCustomerState", acc);
					else {
						System.out.println(ConsoleExtras.ANSI_RED + DollarsBankDao.getError() + ". Try Again!");
						subState = 0;
					}
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			default:
				this.controller.changeState("ErrorState");
				break;
				
		}
	}

	@Override
	public void stop() {
		input = null;
	}
}
