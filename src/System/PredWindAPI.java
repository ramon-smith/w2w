package System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import net.sourceforge.jgrib.GribFile;
import net.sourceforge.jgrib.GribRecordGDS;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

public class PredWindAPI {
	
	private static final String api = "http://forecast.predictwind.com/grib/download?username=";
		private static final String user = "carole.duncan@meteorologyplus.co.nz";
		private static final String pass = "123";
		private static final String source = "GFS";
		private static final String lon = "174.832";
		private static final String lat = "-41.243";
//		private static final String res = "1";
		private static final String res = "8";
//		private static final String res = "60";
//		private static final String timestep = "1";
		private static final String timestep = "3";
//		private static final String timestep = "12";
		private static final String forecast = "all";
		private static final String compress = "true";
		
		public static void main (String args[]) throws IOException{
			
			double ak1lat = -36.79;
			double ak1long = 174.986;
			double ak8lat = -36.443;
			double ak8long = 174.443;
			double bkla = 11.827;
			double bklo = 101.596;
			 
				URL d = new URL(api+user+
						"&password="+pass+
						"&source="+source+
						"&lon="+ak8long+
						"&lat="+ak8lat+
						"&resolution="+res+
						"&timestep="+timestep+
						"&forecast="+forecast+
						"&compress="+compress);
				
				Files.copy(d.openStream(), new File("auckland8.grb").toPath(), StandardCopyOption.REPLACE_EXISTING);
				
				
			      // Reading of grib files must be inside a try-catch block
			      try {
			         // Create GribFile instance
			         GribFile gribFile = new GribFile("auckland8.grb");
			         GribRecordGDS[] s = gribFile.getGridsForType("PRATE");
			         
			         // Dump verbose inventory for each record
			         gribFile.listRecords(System.out);
			         
			      } catch (FileNotFoundException noFileError) {
			         System.err.println("FileNotFoundException : " + noFileError);
			      } catch (IOException ioError) {
			         System.err.println("IOException : " + ioError);
			      } catch (NoValidGribException noGrib) {
			         System.err.println("NoValidGribException : " + noGrib);
			      } catch (NotSupportedException noSupport) {
			         System.err.println("NotSupportedException : " + noSupport);
			      }
		}
}
