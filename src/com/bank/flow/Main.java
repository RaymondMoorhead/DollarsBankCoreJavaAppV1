package com.bank.flow;

import com.bank.flow.console.*;

public class Main {

	public static void main(String[] args) {
		StateController controller = new StateController();
		
		// add your states here
		controller.addState(new WelcomeState());
		controller.addState(new NewAccountState());
		controller.addState(new ErrorState());
		controller.addState(new LoginState());
		controller.addState(new NewAccountState());
		controller.addState(new TransferState());
		controller.addState(new WelcomeCustomerState());
		controller.addState(new DepositState());
		controller.addState(new WithdrawState());
		
		controller.start("WelcomeState"); // the first state to run
	}

}
