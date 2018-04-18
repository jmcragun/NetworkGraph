package assignment11;

import java.util.Comparator;

public class AirportComparator implements Comparator<Airport> {

	@Override
	public int compare(Airport arg0, Airport arg1) {
		//System.out.println("Airport 1:" + arg0.cost());
		//System.out.println("Airport 2:" + arg1.cost());
		double cost = arg0.cost() - arg1.cost();
		if (Math.abs(cost) < 0.000001) {
			return 0;
		} else if (cost < 0) {
			return -1;
		} else {
			return 1;
		}
	}

}
