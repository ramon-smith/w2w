package Entitys;
import java.util.ArrayList;
import javax.persistence.*;
import System.APIControl;
import System.TaskType;

@Entity
public class BuildSite {

	@Id @GeneratedValue 
	long id;
	private String siteName;
//	private int priority;
	private ArrayList<Task> taskList;
//	private Customer parentCustomer;
//	private SiteAccessTime[] allowableWorkTimes;
	private transient APIControl api;
	private double lat;
	private double lon;

	public ArrayList<Task> getTaskList() {
		return this.taskList;
	}

	public void createNewTask(String desc, TaskType type){
		taskList.add(new Task(this,desc,type));
	}
	
	public void createAPI(){
		api = new APIControl(Double.toString(lat),Double.toString(lon));
	}
	
	public APIControl getAPI(){
		if (api == null){
			api = new APIControl(Double.toString(lat),Double.toString(lon));
		}
		
		return api;
	}
	
	public BuildSite(double lati, double longi){
		lat = lati;
		lon = longi;
		taskList = new ArrayList<Task>();
	}

	
	public void getTask(){
		
	}

	public String getSiteName() {
		return siteName;
	}
}