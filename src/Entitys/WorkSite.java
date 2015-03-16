package Entitys;

import java.util.ArrayList;
//import javax.persistence.*;
import System.APIControl;
import System.TaskType;

//@Entity
public class WorkSite {

//	@Id
//	@GeneratedValue
//	long id;
	private String siteName;
	// private int priority;
	private ArrayList<Task> taskList;
	// private Customer parentCustomer;
	// private SiteAccessTime[] allowableWorkTimes;
	private transient APIControl api;
	private double lat;
	private double lon;

	public ArrayList<Task> getTaskList() {
		return this.taskList;
	}

	public Task createNewTask(String desc, TaskType type) {
		Task t = new Task(this, desc, type);
		taskList.add(t);
		return t;
	}

	public void createAPI() {
		api = new APIControl(Double.toString(lat), Double.toString(lon));
	}

	public APIControl getAPI() {
		if (api == null) {
			api = new APIControl(Double.toString(lat), Double.toString(lon));
		}

		return api;
	}

	public WorkSite(double lati, double longi) {
		lat = lati;
		lon = longi;
		taskList = new ArrayList<Task>();
	}

	public void getTask() {

	}
	
	public void clearTasks(){
		taskList.clear();
	}
	
	public boolean isSiteMatch(double lat, double longi){
		return (this.lat == lat && this.lon == longi);
	}

	public String getSiteName() {
		return siteName;
	}
}