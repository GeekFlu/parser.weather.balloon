package mx.com.geekflu.weather.balloon.model;

/**
 * 
 * @author Luis E. Gonzalez
 *
 */
public class DataRow {
	private String timeStamp;
	private String location;
	private String temperature;
	private String observatory;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getObservatory() {
		return observatory;
	}
	public void setObservatory(String observatory) {
		this.observatory = observatory;
	}
}
