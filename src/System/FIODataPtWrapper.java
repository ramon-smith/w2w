package System;

import com.github.dvdme.ForecastIOLib.FIODataPoint;

public class FIODataPtWrapper {
	
	private FIODataPoint dp;
	
	public FIODataPtWrapper(FIODataPoint dataPoint){
		dp = dataPoint;
	}
	
	public static FIODataPtWrapper makeWrapper(FIODataPoint dp){
		return new FIODataPtWrapper(dp);
	}
	
	public String getByKey(String field){
		
		if (field.equalsIgnoreCase("dewIndex"))
			return dewIndex();
		if (field.equalsIgnoreCase("concevap"))
			return concreteEvap();
		
		return dp.getByKey(field);
		
	}
	
	private String dewIndex(){
		double t = Double.valueOf(dp.getByKey("temperature"));
		double dT = Double.valueOf(dp.getByKey("dewPoint"));
		double index = t-dT;
		return String.valueOf(index);
	}
	
	private String concreteEvap(){
		double tA = Double.valueOf(dp.getByKey("temperature"));
		double tC;
		
		if (tA < 18){
			tC = 20;
		}else{
			tC = tA;
		}
		
		double r = Double.valueOf(dp.getByKey("humidity"));
		double v = Double.valueOf(dp.getByKey("windSpeed")) * 3.6 * 0.66666666666;
		
		// 	E = 5 ( [tC + 18]^(2.5) - r[tA + 18]^(2.5) )( v + 4 ) 10^(-6)
		//		E = evaporation rate (kg/m2/h)
		//		tA = Temperature
		//		tC = Concrete Temperature
		//		tC = 20C, when Ta < 18C
		//		tC = Ta, when Ta >= 18C
		//		r = Relative Humidity / 100
		//		v = (Wind speed m/s) x 3.6 x 2/3 
		//		(b/c in kph, and (⅔) to adjust for wind being measured at a height of 10m)
		double ans = 5 * ( Math.pow((tC + 18), 2.5) - r*Math.pow((tA + 18),2.5) ) * (v + 4) * 0.000001;
		return String.valueOf(ans);
	}
	


}
