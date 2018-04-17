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
	}
}
