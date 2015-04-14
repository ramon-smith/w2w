package Tests;
import java.util.ArrayList;
import java.util.HashMap;

import Entitys.WorkSite;
import Entitys.Customer;
import Entitys.Task;
import System.W2W;
import System.TaskColour;
import System.TaskRule;
import System.TaskType;


public class TestAPICalls {

	public static void main(String[] args) {
		
	
		HashMap<String, TaskType> taskTypes = W2W.buildTaskTypes();
		
		Customer c = new Customer("Ramon");
		//BuildSite s = new BuildSite(13.7563,  100.5018);
		WorkSite s = new WorkSite(13.7563,  100.5018);
		//s.createNewTask("paint house", taskTypes.get("painting"));
		s.createNewTask("concrete drive", taskTypes.get("concreting"));
		s.createAPI();
		
		for ( Task t : s.getTaskList() ){
			//t.buildRulesJSONArray();
			t.printHourlyInfo();
			t.printUXHourly();
		}
	}

}
