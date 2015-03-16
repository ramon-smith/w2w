package System;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODataPoint;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;

public class APIControl {

	private static final int UPDATE_AFTER_MINS = 5;
	private DateTime lastUpdate;
	private ForecastIO fio;

	public APIControl(String lat, String lon){
		fio = new ForecastIO(W2W.APIKEY); //instantiate the class with the API key. 
		fio.setExtend(true);
		fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
		fio.setExcludeURL("daily,minutely,alerts,flags");             //excluded the minutely and hourly reports from the reply
		//fio.setTime("2015-03-05T12:00:00+1300");
		fio.getForecast(lat,lon);
		lastUpdate = new DateTime();
		
	}
	

	/**
	 * 
	 * @param tag
	 */
	public double[] getWXVariableData(String tag) {
		// TODO - implement APIControl.getWXVariableData
		throw new UnsupportedOperationException();
	}


	public double getCurrentData(String field){
		updateIfRequired();
		FIOCurrently current = new FIOCurrently(fio);
		return Double.parseDouble(FIODataPtWrapper.makeWrapper(current.get()).getByKey(field));
	}
	
	
	/**
	 * Gives 49 hours of data. If offset is 0, current observations are given as first data point
	 * @param field
	 * @param offset
	 * @return
	 */
	public double[] getHourlyData(String field, int offset){
		updateIfRequired();
		double[] data = new double[49];
		FIOHourly hrly = new FIOHourly(fio);
		int first = 0;
		//use observation data if offset is 0
		if (offset == 0){
			FIOCurrently current = new FIOCurrently(fio);
			//wrap datapoint
			data[0] = Double.parseDouble(FIODataPtWrapper.makeWrapper(current.get()).getByKey(field));
			first = 1;
		}
		
		for (int i = first; i < 49 ; i++){
			data[i] = Double.parseDouble(FIODataPtWrapper.makeWrapper(hrly.getHour(i+offset)).getByKey(field));
		}
		return data;
	}
	
	public DateTime getTimestampForOffset(int offset){
		FIODataPoint data = null;
		
		if (offset == 0){
			FIOCurrently current = new FIOCurrently(fio);
			data = current.get();
		}else {
			FIOHourly hrly = new FIOHourly(fio);
			data = hrly.getHour(offset);
		} 
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
		String time = data.time();
		String tz = data.getTimezone();
		DateTime ts = dtf.withZone(DateTimeZone.forID(tz)).parseDateTime(time).withZone(new DateTime().getZone());
		return ts;
	}
	

	public void updateIfRequired(){
		if (lastUpdate == null){
			fio.update();
			lastUpdate = new DateTime();
		}
		else {
			org.joda.time.Interval i = new Interval(lastUpdate, new DateTime());
			int m = (int) i.toDuration().getStandardMinutes();
			if (m >= UPDATE_AFTER_MINS){
				System.out.print("^");
				fio.update();
				lastUpdate = new DateTime();
			}
		}
	}

}