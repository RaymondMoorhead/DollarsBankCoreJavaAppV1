package com.bank.flow.console;

import java.util.Scanner;

import com.bank.dao.DollarsBankDao;
import com.bank.entity.Account;
import com.bank.flow.State;

public class DepositState extends State {
	Scanner input;
	Account acc;

	public DepositState() {
		super("DepositState");
	}

	@Override
	public void start(Object obj) {
		System.out.println();
		System.out.println("+----------------------+");
		System.out.println("| Enter Deposit Details|");
		System.out.println("+----------------------+");
		input = new Scanner(System.in);
		acc = (Account) obj;
	}

	@Override
	public void run() {
		System.out.println("Amount:" + ConsoleExtras.ANSI_GREEN);
		if(input.hasNext()) {
			String num = input.next();
			if(ConsoleExtras.validAmount(num)) {
				DollarsBankDao.deposit(acc, ConsoleExtras.parseAmount(num));
				this.controller.changeState("WelcomeCustomerState", acc);
			}
			else
				System.out.println(ConsoleExtras.ANSI_RED + "Please Enter A Valid Amount");
		}
		System.out.print(ConsoleExtras.ANSI_RESET);
	}

	@Override
	public void stop() {
		input = null;
		acc = null;
	}
}
