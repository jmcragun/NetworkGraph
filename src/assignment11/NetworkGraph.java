/**
 * @Author Joshua Cragun & Steven Pasinsky
 */
package assignment11;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <p>
 * This class represents a graph of flights and airports along with specific
 * data about those flights. It is recommended to create an airport class and a
 * flight class to represent nodes and edges respectively. There are other ways
 * to accomplish this and you are free to explore those.
 * </p>
 * 
 * <p>
 * Testing will be done with different criteria for the "best" path so be sure
 * to keep all information from the given file. Also, before implementing this
 * class (or any other) draw a diagram of how each class will work in relation
 * to the others. Creating such a diagram will help avoid headache and confusion
 * later.
 * </p>
 * 
 * <p>
 * Be aware also that you are free to add any member variables or methods needed
 * to completed the task at hand
 * </p>
 * 
 * @author CS2420 Teaching Staff - Spring 2018
 */
public class NetworkGraph {

	InputStream IS;

	HashMap<String, Airport> network;

	/**
	 * <p>
	 * Constructs a NetworkGraph object and populates it with the information
	 * contained in the given file. See the sample files or a randomly generated one
	 * for the proper file format.
	 * </p>
	 * 
	 * <p>
	 * You will notice that in the given files there are duplicate flights with some
	 * differing information. That information should be averaged and stored
	 * properly. For example some times flights are canceled and that is represented
	 * with a 1 if it is and a 0 if it is not. When several of the same flights are
	 * reported totals should be added up and then reported as an average or a
	 * probability (value between 0-1 inclusive).
	 * </p>
	 * 
	 * @param flightInfo
	 *            - The inputstream to the flight data. The format is a *.csv(comma
	 *            separated value) file
	 * 
	 */
	public NetworkGraph(InputStream flightInfo) {
		network = new HashMap<>();
		try (Scanner info = new Scanner(flightInfo)) {
			try {
				info.nextLine();
			} catch (NoSuchElementException e) {
				return;
			}
			while (info.hasNextLine()) {
				String line = info.nextLine();
				String[] data = line.split(",");
				if (!network.containsKey(data[0])) {
					Airport airport = new Airport(data[0]);
					// ADD DESTINATION
					// The array should be of size 8
					airport.addDestination(data[1], data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]),
							Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]));
					network.put(data[0], airport);
				} else {
					// ADD ADDITIONAL DESTINATION
					Airport airport = network.get(data[0]);
					airport.addDestination(data[1], data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]),
							Double.parseDouble(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]));
					network.put(data[0], airport);
				}
			}
		}
	}

	/**
	 * This method returns a BestPath object containing information about the best
	 * way to fly to the destination from the origin. "Best" is defined by the
	 * FlightCriteria parameter <code>enum</code>. This method should throw no
	 * exceptions and simply return a BestPath object with information dictating the
	 * result. If the destination or origin is not contained in this instance of
	 * NetworkGraph, simply return a BestPath object with an empty path (not a
	 * <code>null</code> path) and a path cost of 0. If origin or destination are
	 * <code>null</code>, also return a BestPath object with an empty path and a
	 * path cost of 0 . If there is no path in this NetworkGraph from origin to
	 * destination, also return a BestPath with an empty path and a path cost of 0.
	 * 
	 * @param origin
	 *            - The starting location to find a path from. This should be a 3
	 *            character long string denoting an airport.
	 * 
	 * @param destination
	 *            - The destination location from the starting airport. Again, this
	 *            should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria
	 *            - This enum dictates the definition of "best". Based on this value
	 *            a path should be generated and return.
	 * 
	 * @return - An object containing path information including origin,
	 *         destination, and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria) {

		BestPath bestPath = new BestPath(new ArrayList<String>(), 0);

		PriorityQueue<Airport> priorityQueue = new PriorityQueue<Airport>(new AirportComparator());

		// First check to see if the wanted airports were read.
		if (!network.containsKey(origin) || !network.containsKey(destination)) {
			return bestPath;
		}

		Airport startPort = network.get(origin);
		Airport finishPort = network.get(destination);

		Airport currentPort;

		startPort.setCost(0);

		priorityQueue.add(startPort);

		while (!priorityQueue.isEmpty()) {
			currentPort = priorityQueue.deleteMin();
			currentPort.visit();
			if (currentPort.equals(finishPort)) {
				bestPath.setPathCost(currentPort.cost());
				for (Airport endPath = currentPort; endPath != null; endPath = endPath.cameFrom()) {
					bestPath.addAirport(endPath);
				}
				break;
			}
			for (Destination flight : currentPort.destinations().values()) {
				if (network.containsKey(flight.destinationCity())) {
					Airport dest = network.get(flight.destinationCity());
					if (!dest.isVisited()) {
						if ((dest.cost() > currentPort.cost() + flight.getValue(criteria)) && (flight.getValue(criteria) >= 0)) {
							dest.cameFrom(currentPort);
							dest.setCost(currentPort.cost() + currentPort.getLocalCost(criteria, dest.city()));
							priorityQueue.add(dest);
						}
					}
				}
			}
		}
		resetNetwork();
		return bestPath;
	}

	/**
	 * <p>
	 * This overloaded method should do the same as the one above only when looking
	 * for paths skip the ones that don't match the given airliner.
	 * </p>
	 * 
	 * @param origin
	 *            - The starting location to find a path from. This should be a 3
	 *            character long string denoting an airport.
	 * 
	 * @param destination
	 *            - The destination location from the starting airport. Again, this
	 *            should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria
	 *            - This enum dictates the definition of "best". Based on this value
	 *            a path should be generated and return.
	 * 
	 * @param airliner
	 *            - a string dictating the airliner the user wants to use
	 *            exclusively. Meaning no flights from other airliners will be
	 *            considered.
	 * 
	 * @return - An object containing path information including origin,
	 *         destination, and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria, String airliner) {
		BestPath bestPath = new BestPath(new ArrayList<String>(), 0);

		PriorityQueue<Airport> priorityQueue = new PriorityQueue<Airport>(new AirportComparator());

		// First check to see if the wanted airports were read.
		if (!network.containsKey(origin) || !network.containsKey(destination)) {
			return bestPath;
		}

		Airport startPort = network.get(origin);
		Airport finishPort = network.get(destination);

		Airport currentPort;

		startPort.setCost(0);

		priorityQueue.add(startPort);

		while (!priorityQueue.isEmpty()) {
			currentPort = priorityQueue.deleteMin();
			currentPort.visit();
			if (currentPort.equals(finishPort)) {
				bestPath.setPathCost(currentPort.cost());
				for (Airport endPath = currentPort; endPath != null; endPath = endPath.cameFrom()) {
					bestPath.addAirport(endPath);
				}
				break;
			}
			for (Destination flight : currentPort.destinations().values()) {
				if (network.containsKey(flight.destinationCity())) {
					Airport dest = network.get(flight.destinationCity());
					if (!dest.isVisited()) {
						if ((dest.cost() > currentPort.cost() + flight.getValue(criteria, airliner)) && (flight.getValue(criteria, airliner) >= 0)) {
							dest.cameFrom(currentPort);
							dest.setCost(currentPort.cost() + currentPort.getLocalCost(criteria, dest.city(), airliner));
							priorityQueue.add(dest);
						}
					}
				}
			}
		}
		resetNetwork();
		return bestPath;
	}

	// helper dijkstra's
	
	private void resetNetwork() {
		for (Airport airport: network.values()) {
			airport.setCost(Integer.MAX_VALUE);
			airport.unvisit();
			airport.cameFrom(null);
		}
	}
}
