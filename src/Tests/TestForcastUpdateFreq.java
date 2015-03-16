package Tests;

import org.joda.time.DateTime;

import Entitys.WorkSite;
import System.APIControl;

public class TestForcastUpdateFreq {

	public static void main(String[] args) throws InterruptedException {
		
		
		WorkSite s = new WorkSite(-36.502550,  174.442353);
		s.createAPI();
		APIControl api = s.getAPI();
		
		double t = api.getCurrentData("temperature");
		double ws = api.getCurrentData("windSpeed");
		double ps = api.getCurrentData("pressure");
		
		System.out.println("Starting at " + DateTime.now());
		boolean printTime = false;
		
		while (true){
			double t1 = api.getCurrentData("temperature");
			double ws1 = api.getCurrentData("windSpeed");
			double ps1 = api.getCurrentData("pressure");
			
			if (t1 != t){
				System.out.println("\nTemperature forecast has changed");
				printTime = true;
			}
			if (ws1 != ws){
				System.out.println("\nwindspeed forecast has changed");
				printTime = true;
			}
			if (ps1 != ps){
				System.out.println("\npressure forecast has changed");
				printTime = true;
			}
			
			if (printTime)
				System.out.println(DateTime.now());
			
			printTime = false;
			ws = ws1;
			t = t1;
			ps = ps1;
			
			Thread.sleep(30000l);
		}

	}

}
