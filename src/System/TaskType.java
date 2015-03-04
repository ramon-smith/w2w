package System;

import java.util.ArrayList;

public class TaskType {

	/**
	 * ArrayList
	 */
	private ArrayList<TaskRule> rules;
	
	public TaskType(){
		rules = new ArrayList<>();
	}
	
	public void addRule(TaskRule rule){
		rules.add(rule);
	}

	public ArrayList<TaskRule> getRules() {
		return rules;
	}
	

}