package mx.com.geekflu;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mx.com.geekflu.weather.balloon.WeatherBalloonStatistics;
import mx.com.geekflu.weather.balloon.model.Statistics;
import mx.com.geekflu.weather.balloon.service.BalloonWeatherParserService;
import mx.com.geekflu.weather.balloon.service.BalloonWeatherParserServiceImpl;
import mx.com.geekflu.weather.balloon.util.Constants;

/**
 * Unit test for simple App.
 */
public class AppTest extends WeatherBalloonStatistics {
	
	private BalloonWeatherParserService balloonService;
	
	@Before
	public void setUp() {
		System.out.println("Before...");
		balloonService = new BalloonWeatherParserServiceImpl();
	}

	@Test
	@Ignore
	public void generate_random_data() {
		balloonService.generateData("C:\\Users\\luisgonz\\Documents\\balloon_data.txt", 10, 10);
	}
	
	@Test
	public void generate_statistics() {
		Statistics s = balloonService.calculateAndGenerateOutput("C:\\Users\\luisgonz\\Documents\\balloon_data.txt", Constants.DISTANCE_UNIT_METER, Constants.TEMPERATURE_UNIT_CELSISUS);
		System.out.println(s);
	}
	
	@Test
	@Ignore
	public void test_main_generate_outputdata() {
//		main(args);
	}
}
