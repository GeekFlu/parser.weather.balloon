package mx.com.geekflu.weather.balloon.model;

/**
 * 
 * @author Luis E. Gonzalez
 *
 */
public class DataRow {
	private String timeStamp;
	private int x;
	private int y;
	private int temperature;
	private String observatory;
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public String getObservatory() {
		return observatory;
	}
	public void setObservatory(String observatory) {
		this.observatory = observatory;
	}
}
