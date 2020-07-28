package com.bank.flow.console;

import com.bank.flow.State;

public class ErrorState extends State{

	public ErrorState() {
		super("ErrorState");
	}

	@Override
	public void start(Object obj) {
	}

	@Override
	public void run() {
		System.out.println(ConsoleExtras.ANSI_RED + "\nAn Unknown Error Has Occurred" + ConsoleExtras.ANSI_RESET);
		this.controller.changeState("WelcomeState");
	}

	@Override
	public void stop() {
		
	}

}
