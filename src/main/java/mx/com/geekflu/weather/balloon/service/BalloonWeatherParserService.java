package mx.com.geekflu.weather.balloon.service;

import java.util.List;

import mx.com.geekflu.weather.balloon.model.DataRow;

public interface BalloonWeatherParserService {
	
	List<DataRow> readData();

}
