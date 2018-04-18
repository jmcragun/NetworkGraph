package assignment11;

import org.junit.Test;
import static org.junit.Assert.*;

public class DestinationAndAirportTests {
	@Test
	public void test1() {
		Airport slc = new Airport("SLC");
		
		slc.addDestination("LA", "AA", 200, 0, 400, 0.1, 160);
		slc.addDestination("LA", "AA", 400, 4, 800, 0, 160);
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.PRICE, "LA") - 300) < 0.001);
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.DELAY, "LA") - 2) < 0.001);
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.DISTANCE, "LA") - 600) < 0.001);
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.CANCELED, "LA") - 0.05) < 0.001);
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.TIME, "LA") - 160) < 0.001);
	}//Write a couple more test
	
	@Test
	public void test2() {
		Airport slc = new Airport("SLC");
		
		slc.addDestination("JFK", "AA", 204, 1, 400, 2402, 720.20);
		slc.addDestination("JFK", "DL", 204, 1, 200, 2402, 1000);
		
		Airport guy = new Airport("GUY");
		
		guy.addDestination("JFK", "DL", 100, 0, 178, 2000, 1500);
		
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.PRICE, "JFK", "AA")) < Math.abs(slc.getLocalCost(FlightCriteria.PRICE, "JFK", "DL")));
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.DELAY, "JFK", "AA")) > Math.abs(guy.getLocalCost(FlightCriteria.DELAY, "JFK", "DL")));
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.DISTANCE, "JFK", "AA")) > Math.abs(guy.getLocalCost(FlightCriteria.PRICE, "JFK", "DL")));
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.CANCELED, "JFK", "AA")) > Math.abs(guy.getLocalCost(FlightCriteria.CANCELED, "JFK", "DL")));
		assertTrue(Math.abs(slc.getLocalCost(FlightCriteria.TIME, "JFK", "AA")) > Math.abs(guy.getLocalCost(FlightCriteria.TIME, "JFK", "DL")));
	}
	
	@Test
	public void testCameFrom() {
		Airport slc = new Airport("SLC");
		
		slc.addDestination("LA", "AA", 200, 0, 400, 0.1, 160);
		slc.addDestination("LA", "AA", 400, 4, 800, 0, 160);
		Airport lax = new Airport("LA");
		lax.cameFrom(slc);
		assertEquals(lax.cameFrom(), slc);
	}
}
