package com.bank.flow.console;

import java.util.Scanner;

import com.bank.dao.DollarsBankDao;
import com.bank.entity.Account;
import com.bank.flow.State;

public class NewAccountState extends State{

	Scanner input;
	Account newAcc;
	int subState;

	public NewAccountState() {
		super("NewAccountState");
	}

	@Override
	public void start(Object obj) {
		System.out.println("+-------------------------------+");
		System.out.println("| Enter Details For New Account |");
		System.out.println("+-------------------------------+");
		input = new Scanner(System.in);
		newAcc = new Account();
		subState = 0;
	}

	@Override
	public void run() {
		switch(subState) {
			case 0:
				System.out.println("Customer Name:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					newAcc.setName(input.next());
					++subState;
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 1:
				System.out.println("Customer Address:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					newAcc.setAddress(input.next());
					++subState;
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 2:
				System.out.println("Customer Contact Number:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					String phone = input.next();
					if(Account.validPhone(phone)) {
						newAcc.setContactNumber(phone);
						++subState;
					}
					else
						System.out.println(ConsoleExtras.ANSI_RED + "Invalid Phone Number");
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 3:
				System.out.println("User Id :" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					String id = input.next();
					if(DollarsBankDao.idIsUnique(id)) {
						newAcc.setUserId(id);
						++subState;
					}
					else
						System.out.println(ConsoleExtras.ANSI_RED + "Please Use A Unique Id");
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 4:
				System.out.println("Password : 8 Characters With Lower, Upper & Special" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					String pass = input.next();
					String error = Account.validPassword(pass);
					if(error == null) {
						newAcc.setUserId(pass);
						++subState;
					}
					else
						System.out.println(ConsoleExtras.ANSI_RED + error);
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 5:
				System.out.println("Initial Deposite Amount:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					String num = input.next();
					if(ConsoleExtras.validAmount(num)) {
						newAcc.addAmount(ConsoleExtras.parseAmount(num), "Initial Balance");
						this.controller.changeState("WelcomeCustomerState", newAcc);
					}
					else
						System.out.println(ConsoleExtras.ANSI_RED + "Please Enter A Valid Amount");
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
		if(!DollarsBankDao.addAccount(newAcc))
			System.out.println(ConsoleExtras.ANSI_RED + "User Cannot Be Added For An Unknown Reason" + ConsoleExtras.ANSI_RESET);
		newAcc = null;
	}
}
