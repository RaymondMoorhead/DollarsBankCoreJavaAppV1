package com.bank.flow.console;

import java.util.Scanner;

import com.bank.flow.State;

public class WelcomeState extends State{

	Scanner input;

	public WelcomeState() {
		super("WelcomeState");
	}

	@Override
	public void start(Object unused) {
		System.out.println();
		System.out.println("+---------------------------+");
		System.out.println("| DOLLARSBANK Welcomes You! |");
		System.out.println("+---------------------------+");
		System.out.println("1. Create New Account");
		System.out.println("2. Login");
		System.out.println("3. Exit");
		input = new Scanner(System.in);
	}

	@Override
	public void run() {
		System.out.println(ConsoleExtras.ANSI_GREEN + "\nEnter Choice (1,2 or 3) :" + ConsoleExtras.ANSI_RESET);
		if(input.hasNextInt()) {
			switch(input.nextInt()) {
			case 1:
				this.controller.changeState("NewAccountState");
			case 2:
				this.controller.changeState("LoginState");
			case 3:
				this.controller.shutdown();
				System.out.println("Goodbye");
				break;
			default:
				System.out.println(ConsoleExtras.ANSI_RED + "Invalid Choice" + ConsoleExtras.ANSI_RESET);
			}
		}
	}

	@Override
	public void stop() {
		input = null;
	}

}
