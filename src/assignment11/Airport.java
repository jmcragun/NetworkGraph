package assignment11;

import java.util.HashMap;

/**
 * Data structure that represents a single airport for assignment 11
 * 
 * @author Joshua Cragun & Steven Pasinsky
 *
 */
public class Airport {
	/**
	 * Cost in getting to this airport. It is initially set to Integer.MAX_VAL until
	 * update by D's A
	 */
	private double cost;

	private HashMap<String, Destination> destinations;
	

	/** Denotes the city of the airport */
	private String city;

	/** Denotes the previous airport on the optimal */
	private Airport cameFrom;

	private boolean visited;

	public Airport(String city) {
		cost = Integer.MAX_VALUE;
		this.city = city;
		cameFrom = null;
		visited = false;
		destinations = new HashMap<>();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Airport) {
			Airport other = (Airport) o;
			return city.equals(other.city());
		}
		return false;
	}

	public String city() {
		return city;
	}

	public Airport cameFrom() {
		return cameFrom;
	}
	
	public void cameFrom(Airport airport) {
		cameFrom = airport;
	}

	public HashMap<String, Destination> destinations() {
		return destinations;
	}

	public double cost() {
		return cost;
	}

	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * Gets the immediate cost from a given airport
	 * @param fc
	 * @param destination
	 * @return
	 */
	public double getLocalCost(FlightCriteria fc, String destination) {
		// Get all destinations with the destination city, average the specified criteria, and return it
		return destinations.get(destination).getValue(fc);
	}
	
	public double getLocalCost(FlightCriteria fc, String destination, String carrier) {
		// Get all destinations with the destination and carrier, average the specified criteria, and return it
		return destinations.get(destination).getValue(fc, carrier);
	}

	/**
	 * Sets the overhead cost to a specified value.
	 * 
	 * With context to Dijikstra's algorithm, this will be the cost of getting to
	 * this airport from a given starting location
	 * 
	 * @param newCost
	 */
	public void setCost(double newCost) {
		cost = newCost;
	}

	/**
	 * This method allows the user to add destinations to a given airport
	 * 
	 * @param airport
	 * @param localCost
	 */
	public void addDestination(String city, String carrier, double price, double delay, double distance, double canceled, double time) {
		if (!destinations.containsKey(city)) {
			Destination newDestination = new Destination(city);
			newDestination.addData(carrier, price, delay, distance, canceled, time);
			destinations.put(city, newDestination);
		} else {
			Destination destination = destinations.get(city);
			destination.addData(carrier, price, delay, distance, canceled, time);
			destinations.put(city, destination);
		}
	}
	
	public void switchVistited() {
		visited ^= true;
	}
	
	@Override
	public int hashCode() {
		return city.hashCode();
	}
}
