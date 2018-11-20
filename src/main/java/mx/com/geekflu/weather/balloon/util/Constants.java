package mx.com.geekflu.weather.balloon.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import mx.com.geekflu.weather.balloon.model.Observatory;

public class Constants {
	public static final int MIN = 0;
	public static final int MAX = 1000;
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm");
	
	public static final String PIPE = "|";
	public static final String COMMA = ",";
	
	public static final String DISTANCE_UNIT_KM = "km";
	public static final String DISTANCE_UNIT_MILES = "miles";
	public static final String DISTANCE_UNIT_METER = "m";

	public static final String TEMPERATURE_UNIT_CELSISUS = "celsius";
	public static final String TEMPERATURE_UNIT_FAHRENHEIT = "fahrenheit";
	public static final String TEMPERATURE_UNIT_KELVIN = "kelvin";
	public static final int DEFAULT_SIZE_BLOCK = 5000;
	
	public static String[] OBSERVATORIES_NAME = {"AU", "US", "FR", "OTHERS"};
	public static Map<String, Observatory> OBSERVATORIES = new HashMap<>();
	static {
		OBSERVATORIES.put("AU", new Observatory("AU", TEMPERATURE_UNIT_CELSISUS, DISTANCE_UNIT_KM));
		OBSERVATORIES.put("US", new Observatory("US", TEMPERATURE_UNIT_FAHRENHEIT, DISTANCE_UNIT_MILES));
		OBSERVATORIES.put("FR", new Observatory("FR", TEMPERATURE_UNIT_KELVIN, DISTANCE_UNIT_METER));
		OBSERVATORIES.put("OTHERS", new Observatory("OTHERS", TEMPERATURE_UNIT_KELVIN, DISTANCE_UNIT_KM));
	}
	
	
	public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = getRandomNumberInRange(1, 28);
        int month = getRandomNumberInRange(1, 12);
        int year = getRandomNumberInRange(startYear, endYear);
        return LocalDate.of(year, month, day);
    }
	
	public static LocalTime createRandomTime() {
		int hour = getRandomNumberInRange(0, 23);
		int minute = getRandomNumberInRange(0, 59);
		return LocalTime.of(hour, minute);
	}
	
	public static int getRandomNumberInRange(int start, int end) {
		if (start >= end) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return start + (int)Math.round(Math.random() * (end - start));
	}
	
	private Constants() {
	}
}
