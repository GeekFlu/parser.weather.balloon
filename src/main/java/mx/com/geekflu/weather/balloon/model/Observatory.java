package mx.com.geekflu.weather.balloon.model;

public class Observatory {
	private String name;
	private String temperatureMetricUnit;
	private String distanceMetricUnit;
	public Observatory(String name, String temperatureMetricUnit, String distanceMetricUnit) {
		super();
		this.name = name;
		this.temperatureMetricUnit = temperatureMetricUnit;
		this.distanceMetricUnit = distanceMetricUnit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemperatureMetricUnit() {
		return temperatureMetricUnit;
	}
	public void setTemperatureMetricUnit(String temperatureMetricUnit) {
		this.temperatureMetricUnit = temperatureMetricUnit;
	}
	public String getDistanceMetricUnit() {
		return distanceMetricUnit;
	}
	public void setDistanceMetricUnit(String distanceMetricUnit) {
		this.distanceMetricUnit = distanceMetricUnit;
	}
}
