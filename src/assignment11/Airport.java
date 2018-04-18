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

	/**
	 * Constructor
	 * @param city
	 */
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


	@Override
	public int hashCode() {
		return city.hashCode();
	}
	
	/**
	 * Returns the city label of the airport. There should only be one airport for a
	 * given city.
	 * 
	 * @return
	 */
	public String city() {
		return city;
	}

	/**
	 * Indicates the airport that this one came from on the optimal path.
	 * 
	 * @return
	 */
	public Airport cameFrom() {
		return cameFrom;
	}

	/**
	 * Sets the optimal airport to come from.
	 * 
	 * @param airport
	 */
	public void cameFrom(Airport airport) {
		cameFrom = airport;
	}

	/**
	 * Returns a hash map of the city labels (key) and destinations (values).
	 * 
	 * @return
	 */
	public HashMap<String, Destination> destinations() {
		return destinations;
	}

	/**
	 * Fetches the overall cost from a given starting point. If this has not been
	 * visited yet, then the cost should be Integer.MAX_VAL.
	 * 
	 * @return
	 */
	public double cost() {
		return cost;
	}

	/**
	 * Returns whether or not this airport has been visited
	 * @return
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Gets the immediate cost from a given airport
	 * 
	 * @param fc
	 * @param destination
	 * @return
	 */
	public double getLocalCost(FlightCriteria fc, String destination) {
		// Get all destinations with the destination city, average the specified
		// criteria, and return it
		return destinations.get(destination).getValue(fc);
	}

	public double getLocalCost(FlightCriteria fc, String destination, String carrier) {
		// Get all destinations with the destination and carrier, average the specified
		// criteria, and return it
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
	public void addDestination(String city, String carrier, double delay, double canceled, double time,
			double distance, double price) {
		if (!destinations.containsKey(city)) {
			Destination newDestination = new Destination(city);
			newDestination.addData(carrier, delay, canceled, time, distance, price);
			destinations.put(city, newDestination);
		} else {
			Destination destination = destinations.get(city);
			destination.addData(carrier, delay, canceled, time, distance, price);
			destinations.put(city, destination);
		}
	}

	/**
	 * Marks the airport as visited
	 */
	public void visit() {
		visited = true;
	}
	
	public void unvisit() {
		visited = false;
	}
}
