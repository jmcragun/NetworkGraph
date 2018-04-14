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

	/** A hash map with the local costs of each linked airport */
	private HashMap<Airport, double[]> links;

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
		links = new HashMap<>();
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

	public Airport comeFrom() {
		return cameFrom;
	}

	public HashMap<Airport, double[]> destinations() {
		return links;
	}

	public double cost() {
		return cost;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public double[] getDestinationCost(Airport airport) {
		return links.get(airport);
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
	public void addDestination(Airport airport, double[] localCosts) {
			links.put(airport, localCosts);
	}

}
