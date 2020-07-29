package com.bank.flow.console;

import java.util.Scanner;

import com.bank.dao.DollarsBankDao;
import com.bank.entity.Account;
import com.bank.flow.State;

public class TransferState extends State{
	Scanner input;
	Account acc;
	String targetId;
	long amount;
	int subState;

	public TransferState() {
		super("TransferState");
	}

	@Override
	public void start(Object obj) {
		System.out.println();
		System.out.println("+-----------------------+");
		System.out.println("| Enter Withdraw Details|");
		System.out.println("+-----------------------+");
		input = new Scanner(System.in);
		acc = (Account) obj;
		subState = 0;
	}

	@Override
	public void run() {
		switch(subState) {
			case 0:
				System.out.println("Account Id You Like To Transfer Funds To:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					targetId = input.next();
					if(DollarsBankDao.idExists(targetId))
						++subState;
					else
						System.out.println(ConsoleExtras.ANSI_RED + "Please Enter A Valid Target Id");
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			case 1:
				System.out.println("Amount:" + ConsoleExtras.ANSI_GREEN);
				if(input.hasNext()) {
					String num = input.next();
					if(ConsoleExtras.validAmount(num) && DollarsBankDao.transfer(acc, targetId, ConsoleExtras.parseAmount(num))) {
						this.controller.changeState("WelcomeCustomerState", acc);
					}
					else
						System.out.println(ConsoleExtras.ANSI_RED + "Please Enter A Valid Amount");
				}
				System.out.print(ConsoleExtras.ANSI_RESET);
				break;
			default:
				this.controller.changeState("ErrorState");
		}

	}

	@Override
	public void stop() {
		input = null;
		acc = null;
	}
}
