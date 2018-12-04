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
	private Float temperature;
	private ObervatoryEnum observatory;
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
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public ObervatoryEnum getObservatory() {
		return observatory;
	}
	public void setObservatory(ObervatoryEnum observatory) {
		this.observatory = observatory;
	}
	@Override
	public String toString() {
		return "DataRow [timeStamp=" + timeStamp + ", x=" + x + ", y=" + y + ", temperature=" + temperature
				+ ", observatory=" + observatory + "]";
	}
	
	
	public static void main(String[] args) {
		System.out.println(ObervatoryEnum.valueOf("OTHERS"));
		System.out.println(ObervatoryEnum.valueOf("US"));
		try {
			System.out.println(ObervatoryEnum.valueOf("uso"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
