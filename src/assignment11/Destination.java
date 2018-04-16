package assignment11;

import java.util.HashMap;

/**
 * 
 * @author Joshua Cragun & Steven Pasinsky
 *
 */

public class Destination {
	/** The city the flight is going to */
	private String destination;
	
	/** Used in calculating the rolling average for the overall local cost */
	private int superIter;
	
	/** Denotes the average cost between all carriers*/
	private double[] overallLocalCosts;
	
	/** Stores information about each carrier to this city from the parent airport */
	private HashMap<String, double[]> carriers;
	
	public Destination(String city) {
		destination = city;
		superIter = 0;
		overallLocalCosts = new double[] {0, 0, 0, 0, 0};
	}
	
	/**
	 * Gets the city this destination heads to
	 * @return
	 */
	public String destinationCity() {
		return destination;
	}
	
	/**
	 * Adds data to the destination and computes a rolling average with it and the previous data, both overall and for the specific carrier
	 * @param carrier
	 * @param price
	 * @param delay
	 * @param distance
	 * @param canceled
	 * @param time
	 */
	public void addData(String carrier, double price, double delay, double distance, double canceled, double time) {
		// First update overall local costs
		++superIter;
		overallLocalCosts[0] = overallLocalCosts[0] + ((price - overallLocalCosts[0]) / superIter);
		overallLocalCosts[1] = overallLocalCosts[1] + ((delay - overallLocalCosts[1]) / superIter);
		overallLocalCosts[2] = overallLocalCosts[2] + ((distance - overallLocalCosts[2]) / superIter);
		overallLocalCosts[3] = overallLocalCosts[3] + ((canceled - overallLocalCosts[3]) / superIter);
		overallLocalCosts[4] = overallLocalCosts[4] + ((time - overallLocalCosts[4]) / superIter);
		
		// Now add the carrier to the map, if it is not already present.
		if (!carriers.containsKey(carrier)) {
			double[] values = new double[] {price, delay, distance, canceled, time, 1};
			carriers.put(carrier, values);
		} else {
			double[] values = carriers.get(carrier);
			values[5] = values[5] + 1;
			values[0] = values[0] + ((price - values[0]) / values[5]);
			values[1] = values[1] + ((delay - values[1]) / values[5]);
			values[2] = values[2] + ((distance - values[2]) / values[5]);
			values[3] = values[3] + ((canceled - values[3]) / values[5]);
			values[4] = values[4] + ((time - values[4]) / values[5]);
			carriers.put(carrier, values);
		}
	}
	
	/**
	 * Fetches the overall local cost value of a specified type
	 * @param fc
	 * @return
	 */
	public double getValue(FlightCriteria fc) {
		switch (fc) {
		case PRICE:
			return overallLocalCosts[0];
		case DELAY:
			return overallLocalCosts[1];
		case DISTANCE:
			return overallLocalCosts[2];
		case CANCELED:
			return overallLocalCosts[3];
		case TIME:
			return overallLocalCosts[4];
		}
		return -1;
	}
	
	/**
	 * Fetches the local cost value of a specified type and carrier
	 * @param fc
	 * @param carrier
	 * @return
	 */
	public double getValue(FlightCriteria fc, String carrier) {
		double[] values = carriers.get(carrier);
		if (values == null) {
			return -1;
		}
		switch (fc) {
		case PRICE:
			return values[0];
		case DELAY:
			return values[1];
		case DISTANCE:
			return values[2];
		case CANCELED:
			return values[3];
		case TIME:
			return values[4];
		}
		return -1;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Destination) {
			Destination other = (Destination) o;
			return other.destinationCity().equals(destination);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return destination.hashCode();
	}
}
