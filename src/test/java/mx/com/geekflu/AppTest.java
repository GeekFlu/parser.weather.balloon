package mx.com.geekflu;

import org.junit.Before;
import org.junit.Test;

import mx.com.geekflu.weather.balloon.service.BalloonWeatherParserService;
import mx.com.geekflu.weather.balloon.service.BalloonWeatherParserServiceImpl;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	private BalloonWeatherParserService balloonService;
	
	@Before
	public void setUp() {
		System.out.println("Before...");
		balloonService = new BalloonWeatherParserServiceImpl();
	}

	@Test
	public void generate_random_data() {
		balloonService.generateData("C:\\Users\\luisgonz\\Documents\\balloon_data.txt", 10000000, 10);
	}
	
	
}
