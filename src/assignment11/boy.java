package assignment11;

import java.util.HashMap;

public class boy {

	public static void main(String[] args) {
		HashMap<Airport, Integer> map = new HashMap<>();
		Airport a = new Airport("SLC");
		Airport b = new Airport ("SLC");
		a.addDestination(new Airport("NEV"), 5, 5, 5, 5, 5);
		map.put(a, 1);
		System.out.println(map.containsKey(b));
		System.out.println(a.equals(b));
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
	}

}
