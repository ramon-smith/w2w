package Entitys;
import java.util.ArrayList;

import javax.persistence.*;
import org.joda.time.DateTime;
import org.joda.time.Interval;

@Entity
public class Customer {

	@Id
	private String loginName;
//	private String loginPassword;
//	private int customerID;
//	private int contactDetails;
	@OneToMany(cascade=CascadeType.PERSIST)
	private ArrayList<BuildSite> buildSites;
	private transient DateTime lastContact;

	
	public Customer(String name){
		loginName = name;
		buildSites = new ArrayList<BuildSite>();
	}
	
	public int minsSinceContact(){
		if (lastContact != null){
			Interval i = new Interval(DateTime.now(),lastContact);
			int mins = (int) i.toDuration().getStandardMinutes();
			return mins;
		}
		return Integer.MAX_VALUE;
	}
	
	public void addBuildSite(BuildSite bs){
		buildSites.add(bs);
	}
	
	public void removeBuildSite(){}
	
	public void contacted(){
		lastContact = DateTime.now();
	}

	@Override
	public String toString(){
		return loginName;
	}


}