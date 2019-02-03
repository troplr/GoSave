public class Restaurant {

	private int dollarSign;
	private double distance;
	private String name;
	private double index;
	
	public Restaurant(String name, double distance, int dollarSign) {
		this.name = name;
		this.distance = distance;
		this.dollarSign = dollarSign;
		index = (double) 30000 * (double) dollarSign + distance;
	}
	
	public double getIndex() {
		return index;
	}
	
	public String getName() {
		return name;
	}
}