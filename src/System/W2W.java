/***
 * 
 * Assumptions:
 * -Customer is in the same timezone as this system
 * -API will not run out of calls
 * 
 * 
 */

package System;

import java.util.HashMap;

public class W2W {
	
	public static final String APIKEY = "d1708fec2337a66dfa54feea3d3fbbba";
	public static HashMap<String,TaskType> taskTypes;

	public static HashMap<String,TaskType> buildTaskTypes(){
		HashMap<String, TaskType> tts = new HashMap<>();
		TaskType painting = new TaskType();
		painting.addRule(new TaskRule("temperature", 0, true, 24, TaskColour.RED, "Freezing - curing paint issue", false));
		painting.addRule(new TaskRule("temperature", 3, true, 24, TaskColour.RED, "Too cold", false));
		painting.addRule(new TaskRule("temperature", 10, true, 24, TaskColour.ORANGE, "Cold warning", false));
		painting.addRule(new TaskRule("temperature", 30, false, 0, TaskColour.RED, "Too  hot", false));
		painting.addRule(new TaskRule("windSpeed", 10.3, false, 0, TaskColour.ORANGE, "Wind caution", false));
		painting.addRule(new TaskRule("precipIntensity", 0.1, false, 3, TaskColour.RED, "Rain now, or in next 3 hours. Will affect wet paint", false));
		painting.addRule(new TaskRule("dewIndex", 2, true, 3, TaskColour.RED, "Dew forming on wet paint - spotting, cratering", false));
		painting.addRule(new TaskRule("humidity", 0.85, false, 3, TaskColour.RED, "High humidity, Drying times affected, damage to paint", false));
		painting.addRule(new TaskRule("humidity", 0.1, true, 0, TaskColour.RED, "Low humidity, paint dries too fast", false));
		tts.put("painting", painting);
		
		TaskType concrete = new TaskType();
		concrete.addRule(new TaskRule("concEvap", 0.34, false, 24, TaskColour.RED, "High evaporation - palistic shrinkage cracks", false));
		concrete.addRule(new TaskRule("concEvap", 0.2, false, 24, TaskColour.ORANGE, "High evaporation warning - palistic shrinkage cracks", false));
		concrete.addRule(new TaskRule("temperature", 0, true, 0, TaskColour.RED, "Concrete freezing and thawing cracks", false));
		tts.put("concreting", concrete);
		
		return tts;
	}
	
	public static void main(String[] args) {
		//BUILD TASKS AND RULES
		taskTypes = buildTaskTypes();


	}
	
	public W2W(){
		taskTypes.get("painting");
		
	}

}
