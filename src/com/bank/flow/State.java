package com.bank.flow;

// Add an instance of your extending class to 

public abstract class State {
	
	// unique identifier
	private String name;
	
	public StateController controller;

	public State(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	// called before the first run(), the input Object is
	// any data the last State has passed along
	public abstract void start(Object obj);
	
	// called every loop until this.controller.changeState
	// is called from within
	public abstract void run();
	
	// called after run() returns a non-null value, before the
	// next state starts
	public abstract void stop();
}
