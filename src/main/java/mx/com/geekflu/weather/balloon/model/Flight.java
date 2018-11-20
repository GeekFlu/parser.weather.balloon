package mx.com.geekflu.weather.balloon.model;

/**
 * 
 * 	@author Luis E. Gonzalez
 *	Information reagarding to the flight
 *
 */
public class Flight {
	private String flightNumber;
	private String filePath;
	private Statistics statistics;
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Statistics getStatistics() {
		return statistics;
	}
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	@Override
	public String toString() {
		return "Flight [flightNumber=" + flightNumber + ", filePath=" + filePath + ", statistics=" + statistics + "]";
	}
	
}
