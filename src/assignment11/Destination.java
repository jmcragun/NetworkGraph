package assignment11;

public class Destination {
	private double price;
	private double delay;
	private double distance;
	private double canceled;
	private double time;
	private int iter;
	private String destination;
	private String carrier;
	
	public Destination(String destination, String carrier, double price, double delay, double distance, double canceled, double time) {
		this.destination = destination;
		this.carrier = carrier;
		this.price = price;
		this.delay = delay;
		this.distance = distance;
		this.canceled = canceled;
		this.time = time;
		iter = 1;
	}
	
	public Destination(String destination, String carrier) {
		this.destination = destination;
		this.carrier = carrier;
		iter = 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Destination) {
			Destination other = (Destination) o;
			return (other.carrier().equals(carrier)) && (other.destination().equals(destination));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		String result = destination + carrier;
		return result.hashCode();
	}
	
	public String destination() {
		return destination;
	}
	
	public String carrier() {
		return carrier;
	}
	
	public double getValue(FlightCriteria fc) {
		switch (fc) {
		case PRICE:
			return price;
		case DELAY:
			return delay;
		case DISTANCE:
			return distance;
		case CANCELED:
			return canceled;
		case TIME:
			return time;
		default:
			return -1;
		}
	}
	
	public void update(Destination d) {
		++iter;
		this.price = this.price + ((d.getValue(FlightCriteria.PRICE) - this.price) / iter); 
		this.delay = this.delay + ((d.getValue(FlightCriteria.DELAY) - this.delay) / iter);
		this.distance = this.distance + ((d.getValue(FlightCriteria.DISTANCE) - this.distance) / iter);
		this.canceled = this.canceled + ((d.getValue(FlightCriteria.CANCELED) - this.canceled) / iter);
		this.time = this.time + ((d.getValue(FlightCriteria.TIME) - this.time) / iter);
	}
}
