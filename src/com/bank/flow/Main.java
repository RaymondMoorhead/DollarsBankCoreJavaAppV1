package com.bank.flow;

import com.bank.flow.console.*;

public class Main {

	public static void main(String[] args) {
		StateController controller = new StateController();
		
		// add your states here
		controller.addState(new WelcomeState());
		controller.addState(new NewAccountState());
		
		
		controller.start("WelcomeState"); // the first state to run
	}

}
