package Entitys;
import java.util.ArrayList;
import javax.persistence.*;
import org.joda.time.DateTime;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import System.TaskColour;
import System.TaskRule;
import System.TaskType;

@Entity
public class Task {

	@Id @GeneratedValue 
	long id;
	private TaskType taskType;
	private String taskDescription;
	private boolean active;
	private BuildSite siteParent;
	
	
	public Task(BuildSite site, String desc, TaskType type){
		this.siteParent = site;
		this.taskType = type;
		this.taskDescription = desc;
		active = true;
	}
	
	/**
	 * Used for debugging
	 */
	public void printHourlyInfo(){
		for (int i = 0; i <25 ; i++){
			
			System.out.println("+ "+i+" HOURS "+ siteParent.getAPI().getTimestampForOffset(i));
			
			for (TaskRule tr : taskType.getRules()){
				double[] data = siteParent.getAPI().getHourlyData(tr.getWxProperty(), i);
				boolean result = tr.applyRule(data);
				System.out.print(result ? "PASS" : "FAIL");
				System.out.println(" "+tr.getFailColour()+" "+tr.getFailAlert());
				
			}
		}
	}
	
	/**
	 * @return 
	 * 
	 */
	public JsonArray buildRulesJSONArray(){
		JsonArray arrayOfHours = new JsonArray();
		for (int i = 0 ; i < 48 ; i++){
			JsonObject o = new JsonObject();
			o.add("Time", siteParent.getAPI().getTimestampForOffset(i).getMillis());
			TaskColour hourColour = TaskColour.GREEN;
			for (TaskRule tr : taskType.getRules()){
				double[] data = siteParent.getAPI().getHourlyData(tr.getWxProperty(), i);
				boolean result = tr.applyRule(data);
				if (!result){
					
					o.add("message", tr.getFailAlert());
					// only replace the task colour if it is not already red.
					if (hourColour != TaskColour.RED)
						hourColour = tr.getFailColour();
				}
			}
			o.add("colour", hourColour.toString());
			
			
			arrayOfHours.add(o);
		}
		return arrayOfHours;
	}
	
	public void printUXHourly(){
		DateTime dt;
		System.out.println(" DEBUG day view for "+taskDescription);
		
		for (int i = 0 ; i < 48 ; i++){
			ArrayList<String> msg = new ArrayList<String>();
			dt = siteParent.getAPI().getTimestampForOffset(i);
			TaskColour hourColour = TaskColour.GREEN;
			for (TaskRule tr : taskType.getRules()){
				double[] data = siteParent.getAPI().getHourlyData(tr.getWxProperty(), i);
				boolean result = tr.applyRule(data);
				if (!result){
					
					//clear orange messages if this alert is RED
					if (hourColour.equals(TaskColour.ORANGE) && tr.getFailColour().equals(TaskColour.RED)){
						msg.clear();
					}
					if (!(hourColour.equals(TaskColour.RED) && tr.getFailColour().equals(TaskColour.ORANGE)))
						msg.add(tr.getFailAlert());
					
					
					// only replace the task colour if it is not already red.
					if (hourColour != TaskColour.RED)
						hourColour = tr.getFailColour();
				}
			}
			String col = hourColour.toString();
			if (col.equals("RED")){
				col = "  RED   ";
			}
			if (col.equals("ORANGE")){
				col = " ORANGE ";
			
			}
			if (col.equals("GREEN")){
				col = " GREEN  ";
			}
			String m1="";
			String m2="";
			String m3="";
			
			try{
				m2 = msg.get(0);
				m1 = msg.get(1);
				m3 = msg.get(2);
			} catch(Exception e){
				
			}
			
			System.out.println("------"+dt.toString("dd MMM yy hh:mmaa")+"------");
			System.out.println("   _________  ");
			System.out.println("  |         |  "+m1);
			System.out.println("  | "+col+"|  "+m2);
			System.out.println("  |_________|  "+m3+"\n");
		}
	}

	public boolean isActive() {
		return active;
	}

}