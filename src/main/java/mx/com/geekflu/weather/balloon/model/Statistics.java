package mx.com.geekflu.weather.balloon.model;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 
 * @author luisgonz
 *
 */
public class Statistics {
	private BigDecimal minTemperature;
	private BigDecimal maxTemperature;
	private BigDecimal meanTemperature;
	private HashMap<String, Integer> observationsByObservatory;
	private BigDecimal totalDistanceTravelled;
	public Statistics() {
	}
	public BigDecimal getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(BigDecimal minTemperature) {
		this.minTemperature = minTemperature;
	}
	public BigDecimal getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(BigDecimal maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	public BigDecimal getMeanTemperature() {
		return meanTemperature;
	}
	public void setMeanTemperature(BigDecimal meanTemperature) {
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
	
	@Override
	public String toString() {
		return "Statistics [minTemperature=" + minTemperature + ", maxTemperature=" + maxTemperature
				+ ", meanTemperature=" + meanTemperature + ", observationsByObservatory=" + observationsByObservatory
				+ ", totalDistanceTravelled=" + totalDistanceTravelled + "]";
	}
	
}
