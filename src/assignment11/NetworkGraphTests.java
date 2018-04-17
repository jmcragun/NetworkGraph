package assignment11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class NetworkGraphTests {

	@Test
	public void testConstructorAndBasicPath() {
		File data = new File("Paths.csv");
		NetworkGraph ng = null;
		try {
			InputStream dataStream = new FileInputStream(data);
			ng = new NetworkGraph(dataStream);
		} catch (FileNotFoundException e) {
			System.out.println("The File was not found.");
		}
		if (ng != null) {
			BestPath bp = ng.getBestPath("SFO", "JFK", FlightCriteria.PRICE);
			System.out.println(bp.getPathCost());
			System.out.println(bp.getPath());
		} else {
			System.out.println("There was a problem initializing the Network Graph");
		}

	}
}
