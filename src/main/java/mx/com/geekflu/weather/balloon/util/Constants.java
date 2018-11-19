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
	public static String[] OBSERVATORIES_NAME = {"AU", "US", "FR", "OTHERS"};
	public static Map<String, Observatory> OBSERVATORIES = new HashMap<>();
	static {
		OBSERVATORIES.put("AU", new Observatory("AU", "celsius", "km"));
		OBSERVATORIES.put("US", new Observatory("US", "fahrenheit", "miles"));
		OBSERVATORIES.put("FR", new Observatory("FR", "kelvin", "m"));
		OBSERVATORIES.put("Others", new Observatory("OTHERS", "kelvin", "km"));
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
