/***
 * 
 * Assumptions:
 * -Customer is in the same timezone as this system
 * -API will not run out of calls
 * 
 * 
 */

package System;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import Entitys.Task;
import Entitys.WorkSite;

public class W2W {
	
	public static final String APIKEY = "d1708fec2337a66dfa54feea3d3fbbba";
	public static HashMap<String,TaskType> taskTypes;
	static ArrayList<WorkSite> storedSites;

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
		concrete.addRule(new TaskRule("concEvap", 1, false, 24, TaskColour.RED, "High evaporation - plastic shrinkage cracks", false));
		concrete.addRule(new TaskRule("concEvap", 0.5, false, 24, TaskColour.ORANGE, "High evaporation warning - plastic shrinkage cracks", false));
		concrete.addRule(new TaskRule("temperature", 0, true, 0, TaskColour.RED, "Concrete freezing and thawing cracks", false));
		tts.put("concreting", concrete);
		
		return tts;
	}
	
	public static void main(String[] args) {
		//BUILD TASKS AND RULES
		taskTypes = buildTaskTypes();
		
		//store sites, APIcontrol retained, avoids extra api calls when data is the same
		storedSites = new ArrayList<WorkSite>();
		
		int portNumber = 4144;
		//TODO Multithread Sockets
		//create socket
		while (true){
		 try  {
		         	ServerSocket serverSocket = new ServerSocket(portNumber);
		            Socket clientSocket = serverSocket.accept();
		            PrintWriter out =
		                new PrintWriter(clientSocket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(
		                new InputStreamReader(clientSocket.getInputStream()));
		            
		            System.out.println("---= Connection from "+clientSocket.getRemoteSocketAddress()+" =---");
		            String line = in.readLine();
		            while (!line.equals("endrequest")){
		            	System.out.println("Processing Site " + line);
			            //listen for site info
			            String[] siteinfo = line.split(",");
			            //check storedSites before creating a new one
			            double lat = Double.valueOf(siteinfo[0]);
			            double lon = Double.valueOf(siteinfo[1]);
			            
			            WorkSite site = findWorkSiteInCollection(lat, lon);
			            
			            if (site == null){
			            	System.out.println("Site details recieved. Not stored in system, creating");
			            	site = new WorkSite(lat, lon);
			            	storedSites.add(site);
			            }else{
			            	System.out.println("Site details recieved, reusing stored copy");
			            	site.clearTasks();
			            }
			            
			            String lineIn = in.readLine();
			            while (!lineIn.equals("endsite")){
				            Task madeTask = site.createNewTask("nodesc", taskTypes.get(lineIn));
				            JsonArray jar = madeTask.buildRulesJSONArray();
		
				            for (JsonValue jv : jar.values() ){
				            	System.out.println(jv.toString());
				            	out.println(jv.toString());
				            }
				            out.println("endtask");
				            lineIn = in.readLine();
				            System.out.println("End of task");
			            }
			            System.out.println("End of site\n\n");
			            line = in.readLine();
		            }
		            
		            out.close();
		            in.close();
		            clientSocket.close();
		            serverSocket.close();
		            System.out.println("Client disconnected\n\n");
		        } catch (IOException e) {
		            System.out.println("Exception caught when trying to listen on port "
		                + portNumber + " or listening for a connection");
		            System.out.println(e.getMessage());
		        }
		}

	}
	
	
	/**
	 * Returns a WorkSite from storedSites matching the lat and long given, or null if not found
	 * @param lat
	 * @param lon
	 * @return
	 */
	private static WorkSite findWorkSiteInCollection(double lat, double lon){
		if (storedSites != null && storedSites.size()>0){
			
			for (WorkSite ws : storedSites){
				if (ws.isSiteMatch(lat, lon))
					return ws;
			}
			
		}
		return null;
	}
	
	public W2W(){
		taskTypes.get("painting");
		
	}

}
