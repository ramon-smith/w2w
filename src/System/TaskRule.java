package System;

public class TaskRule {

	private String wxProperty;
	private boolean failIfLTthresh;
	private double threshold;
	private TaskColour failColour;
	private int hoursLookAhead;
	private boolean isHSRule;
	private String failAlert;
	private String failedReason;


	/**
	 * 
	 * @param wxProp Field name of weather property
	 * @param threshold double value that rule compares data to for failure
	 * @param failsIfLess true if rule fails when data is < threshold
	 * @param lookaheadHours how many hours of future data to test against
	 * @param failCol colour to present if this rule fails
	 * @param failMsg message to display if this rule fails
	 * @param healthSafetyRule 
	 */
	public TaskRule(String wxProp, double threshold, boolean failsIfLess, 
			int lookaheadHours, TaskColour failCol, String failMsg, boolean healthSafetyRule){
		this.wxProperty = wxProp;
		this.threshold = threshold;
		this.failIfLTthresh = failsIfLess;
		this.hoursLookAhead = lookaheadHours;
		this.failColour = failCol;
		this.failAlert = failMsg;
		this.isHSRule = healthSafetyRule;
	}

	public boolean applyRule(double[] data){
		failedReason = "";
		int i = 0;
		boolean failed = false;
		while (i <= hoursLookAhead && !failed){
			
			if (failIfLTthresh){
				failed = data[i] < threshold;
			}else{
				failed = data[i] > threshold;
			}
			
			if (failed){
				failedReason = wxProperty + ": " + data[i] + " was " + 
						(failIfLTthresh ? "below" : "above") + " the threshold of " + threshold + ".";
			}
			//TODO Better wording required
			if (failed && i > 0){
				failedReason += " This test looks at forecast data " + hoursLookAhead + 
						" hours ahead. The data causing this alert occurs at " + i + 
						" hours from this time block";
			}
			i++;
		}
		
		return !failed;
		
	}


	public String getWxProperty() {
		return wxProperty;
	}

	public TaskColour getFailColour() {
		return failColour;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public boolean isHSRule() {
		return isHSRule;
	}

	public String getFailAlert() {
		return failAlert;
	}

}


