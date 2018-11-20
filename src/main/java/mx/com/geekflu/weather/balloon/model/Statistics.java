package mx.com.geekflu.weather.balloon.model;

import java.util.HashMap;

import mx.com.geekflu.weather.balloon.util.Constants;

/**
 * 
 * @author luisgonz
 *
 */
public class Statistics {
	private Float minTemperature;
	private Float maxTemperature;
	private Float meanTemperature;
	private HashMap<String, Integer> observationsByObservatory;
	private Double totalDistanceTravelled;
	
	public Statistics() {
		this.observationsByObservatory = new HashMap<>();
		this.observationsByObservatory.put(Constants.OBSERVATORY_AU, 0);
		this.observationsByObservatory.put(Constants.OBSERVATORY_FR, 0);
		this.observationsByObservatory.put(Constants.OBSERVATORY_OTHERS, 0);
		this.observationsByObservatory.put(Constants.OBSERVATORY_US, 0);
	}
	public Float getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(Float minTemperature) {
		this.minTemperature = minTemperature;
	}
	public Float getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(Float maxTemperature) {
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
	public Double getTotalDistanceTravelled() {
		return totalDistanceTravelled;
	}
	public void setTotalDistanceTravelled(Double totalDistanceTravelled) {
		this.totalDistanceTravelled = totalDistanceTravelled;
	}
	@Override
	public String toString() {
		return "Statistics [minTemperature=" + minTemperature + ", maxTemperature=" + maxTemperature
				+ ", meanTemperature=" + meanTemperature + ", observationsByObservatory=" + observationsByObservatory
				+ ", totalDistanceTravelled=" + totalDistanceTravelled + "]";
	}
	
}
