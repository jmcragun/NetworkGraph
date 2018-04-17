package assignment11;

import java.util.Comparator;

public class AirportComparator implements Comparator<Airport>{

	private FlightCriteria fc;
	
	private String carrier;
	
	public AirportComparator(FlightCriteria flc) {
		fc = flc;
		carrier = null;
	}
	
	public AirportComparator(FlightCriteria flc, String company) {
		fc = flc;
		carrier = company;
	}
	
	@Override
	public int compare(Airport arg0, Airport arg1) {
		if (carrier == null) {
			
		} else {
			
		}
		return 0;
	}
	
	public void setCarrier(String arg) {
		carrier = arg;
	}
	
	public void setCriteria(FlightCriteria arg) {
		fc = arg;
	}

}
