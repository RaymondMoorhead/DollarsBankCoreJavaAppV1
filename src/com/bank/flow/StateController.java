package com.bank.flow;

import java.util.HashMap;

public class StateController {

	private HashMap<String, State> states;
	private boolean running = true;
	private State curState = null;
	private State nextState = null;
	private Object passedData = null;
	
	public StateController() {
		states = new HashMap<String, State>();
	}
	
	public void addState(State state) {
		states.put(state.getName(), state);
		state.controller = this;
	}
	
	public void changeState(String name) {
		nextState = states.get(name);
	}
	
	public void changeState(String name, Object passData) {
		passedData = passData;
		nextState = states.get(name);
	}
	
	public void start(String startStateName) {
		curState = states.get(startStateName);
		curState.start(null);
		run();
	}
	
	public void shutdown() {
		running = false;
	}
	
	private void run() {
		while(running) {
			curState.run();
			if(nextState != null) {
				curState.stop();
				curState = nextState;
				nextState = null;
				curState.start(passedData);
				passedData = null;
			}
		}
	}
}
