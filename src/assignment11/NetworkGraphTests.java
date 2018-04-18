package assignment11;

import org.junit.Assert;

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
	
	@Test(expected = FileNotFoundException.class)
	public void testFileNotFound() throws FileNotFoundException {
		File data = new File("NotAFile.csv");
		NetworkGraph ng = null;
		InputStream dataStream = new FileInputStream(data);
		ng = new NetworkGraph(dataStream);
		BestPath bp = ng.getBestPath("SFO", "JFK", FlightCriteria.PRICE);
		System.out.println(bp.getPathCost());
		System.out.println(bp.getPath());
	}
	
	@Test
	public void testPaths2Price() {
		File data = new File("Paths2.csv");
		NetworkGraph ng = null;
		try {
			InputStream dataStream = new FileInputStream(data);
			ng = new NetworkGraph(dataStream);
		} catch(FileNotFoundException e) {
			System.out.println("There aint no darn file in these parts.");
		}
		if (ng != null) {
			BestPath bp = ng.getBestPath("IHN", "GCF", FlightCriteria.PRICE);
			if(bp.getPathCost() != 1053.08) {
				System.out.println("Incorrect Flight Cost Resolution");
			}
			System.out.println(bp.getPathCost());
			System.out.println(bp.getPath());
		}
	}
	
	@Test
	public void testPaths2Delay() {
		File data = new File("Paths2.csv");
		NetworkGraph ng = null;
		try {
			InputStream dataStream = new FileInputStream(data);
			ng = new NetworkGraph(dataStream);
		} catch(FileNotFoundException e) {
			System.out.println("There aint no darn file in these parts.");
		}
		if (ng != null) {
			BestPath bp = ng.getBestPath("IHN", "GCF", FlightCriteria.DELAY);
			if(bp.getPathCost() != 135) {
				System.out.println("Incorrect Flight Cost Resolution");
			}
			System.out.println(bp.getPathCost());
			System.out.println(bp.getPath());
		}
	}
	
	@Test
	public void testPaths2DelayCarrier() {
		File data = new File("Paths2.csv");
		NetworkGraph ng = null;
		try {
			InputStream dataStream = new FileInputStream(data);
			ng = new NetworkGraph(dataStream);
		} catch(FileNotFoundException e) {
			System.out.println("There aint no darn file in these parts.");
		}
		if (ng != null) {
			BestPath bp = ng.getBestPath("IHN", "GCF", FlightCriteria.DELAY, "MQ");
			if(bp.getPathCost() != 351.5) {
				System.out.println("Incorrect Flight Cost Resolution");
			}
			System.out.println(bp.getPathCost());
			System.out.println(bp.getPath());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testConstructorBigData() {
		File data = new File("flights-2017-q3.csv");
		NetworkGraph ng = null;
		try {
			InputStream dataStream = new FileInputStream(data);
			ng = new NetworkGraph(dataStream);
		} catch (FileNotFoundException e) {
			System.out.println("The File was not found.");
		}
	}//Write more tests using generate flight data
}
