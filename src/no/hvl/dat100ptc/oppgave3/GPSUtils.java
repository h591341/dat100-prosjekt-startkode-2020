package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {			
			if (d < min) {				
				min = d;				
			}
		}
		return min; 

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitudes = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		
		return latitudes;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		
		double[] longitudes = new double[gpspoints.length];
		
		for (int i = 0; i< gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		
		return longitudes;
		
	}

	private static int R = 6371000; // jordens radius

	
	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		
		double latitude1, longitude1, latitude2, longitude2;

		double lat1 = Math.toRadians(gpspoint1.getLatitude());
		double lat2 = Math.toRadians(gpspoint2.getLatitude());
		double lon1 = Math.toRadians(gpspoint1.getLongitude());
		double lon2 = Math.toRadians(gpspoint2.getLongitude());
	
		double DeltaPhi = lat2 - lat1;
		double DeltaLam = lon2 - lon1;
		
		double a = Math.pow((Math.sin(DeltaPhi/2)),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(DeltaLam/2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		double d = R * c;
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

		secs = gpspoint2.getTime() - gpspoint1.getTime();
		double distance = distance(gpspoint1, gpspoint2);

		return 3.6 * distance / secs;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";


		int hrs = secs / 3600;
		int min = secs % 3600 / 60;
		int sec = secs % 60;
		
		// %02d betyr "formatter tallet med 2 siffer, og sett inn 0 i begynnelsen om det trengs"
		return String.format("  %02d:%02d:%02d", hrs, min, sec);
		

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str = " ";

			double factor = Math.pow(10, 2);
			
			d = d * factor;
			double tmp = Math.round(d);
			double rounded = tmp / factor;
			str += rounded;
			
			while (str.length() < TEXTWIDTH) {
				str = " " + str;
			}
			
			return str;
		}		

}
