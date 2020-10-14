package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;


public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {
		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);
		gpspoints = gpscomputer.getGPSPoints();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {
		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);
		showRouteMap(MARGIN + MAPYSIZE);
		showStatistics();
	}
	
	public double xstep() {

		double max = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double min = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double xstep = MAPXSIZE / (Math.abs(max - min)); 
		return xstep;
	}

	public double ystep() {
	
		double maxx = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minn = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		double ystep = MAPYSIZE / (Math.abs(maxx - minn));
				
		return ystep;
		
	}

	public void showRouteMap(int ybase) {
		
		int x, y;
		
		int radius = 3;
		
		int circleId, fillId;
		
		int X = MARGIN + (int) ((gpspoints[0].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints))) * xstep());
		int Y = ybase - (int) ((gpspoints[0].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints))) * ystep());
		
		setColor(0, 255, 0);
		fillCircle(X, Y, radius);
		drawCircle(X, Y, radius);
		
		fillCircle(X, Y, 5);
		circleId = drawCircle(X, Y, 5);
		
		for(int i = 1; i < gpspoints.length; i++) {
			x = MARGIN + (int) ((gpspoints[i].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints))) * xstep());
			y = ybase - (int) ((gpspoints[i].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints))) * ystep());
			
			System.out.println(x + ", " + y);
			
			drawLine(X, Y, x, y);
			X = x;
			Y = y;
			
			if(i == gpspoints.length - 1) {
				setColor(0, 0, 255);
				radius = 5;
			}
			fillCircle(x, y, radius);
			drawCircle(x, y, radius);
		}
	}
	
	public void showStatistics() {
		int TEXTDISTANCE = 20;
		setColor(0,0,0);
		setFont("Courier",12);
		String text[] =    {"Total time", 
							"Total distance", 
							"Total elevation", 
							"Max speed", 
							"Average speed", 
							"Energy"};
		String statistics[] =  {"    " + GPSUtils.formatTime(gpscomputer.totalTime()),
								" " + GPSUtils.formatDouble(gpscomputer.totalDistance()/1000) + " km", 
								" " + GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m",
								" " + GPSUtils.formatDouble(gpscomputer.maxSpeed()) + " km/t",
								" " + GPSUtils.formatDouble(gpscomputer.averageSpeed()) + " km/t", 
								" " + GPSUtils.formatDouble(gpscomputer.totalKcal(100)) + " kcal"};		
		
		for(int i = 0; i < statistics.length; i++) {
			drawString(text[i], TEXTDISTANCE, TEXTDISTANCE + i*TEXTDISTANCE);
			drawString(" :" + statistics[i], TEXTDISTANCE*5, TEXTDISTANCE + i*TEXTDISTANCE);
		}
	}

}
