package mx.com.geekflu.weather.balloon.service;

import mx.com.geekflu.weather.balloon.model.Statistics;

/**
 * 
 * @author Luis E. Gonzalez
 *
 */
public interface BalloonWeatherParserService {
	Statistics calculateAndGenerateOutput(String filePath, String outputFile, String distanceUnit, String temperatureUnit);
	void generateData(String filePath, int numLines2Generate, int fakeDataPercentage);
}
