package mx.com.geekflu.weather.balloon.model;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 
 * @author luisgonz
 *
 */
public class Statistics {
	private Integer minTemperature;
	private Integer maxTemperature;
	private Float meanTemperature;
	private HashMap<String, Integer> observationsByObservatory;
	private BigDecimal totalDistanceTravelled;
	
	public Statistics() {
	}
	public Integer getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(Integer minTemperature) {
		this.minTemperature = minTemperature;
	}
	public Integer getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(Integer maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	public Float getMeanTemperature() {
		return meanTemperature;
	}
	public void setMeanTemperature(Float meanTemperature) {
		this.meanTemperature = meanTemperature;
	}
	public HashMap<String, Integer> getObservationsByObservatory() {
		return observationsByObservatory;
	}
	public void setObservationsByObservatory(HashMap<String, Integer> observationsByObservatory) {
		this.observationsByObservatory = observationsByObservatory;
	}
	public BigDecimal getTotalDistanceTravelled() {
		return totalDistanceTravelled;
	}
	public void setTotalDistanceTravelled(BigDecimal totalDistanceTravelled) {
		this.totalDistanceTravelled = totalDistanceTravelled;
	}
}
