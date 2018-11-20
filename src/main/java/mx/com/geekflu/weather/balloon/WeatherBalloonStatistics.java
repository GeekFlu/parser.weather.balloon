package mx.com.geekflu.weather.balloon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import mx.com.geekflu.weather.balloon.model.CommandNotFoundException;
import mx.com.geekflu.weather.balloon.model.Statistics;
import mx.com.geekflu.weather.balloon.service.BalloonWeatherParserService;
import mx.com.geekflu.weather.balloon.service.BalloonWeatherParserServiceImpl;
import mx.com.geekflu.weather.balloon.util.Constants;

/**
 * 
 * @author Luis E. Gonzalez The tool takes "balloonWeatherInfoFile", "command"
 *         as command line arguments ej: java -jar
 *         target/parser-jar-with-dependencies.jar
 *         --command=[meanTemp],[maxTemp],[minTemp],[observations],[totalDistance]
 *         --generate-data=/path/to/file
 *         --balloonWeatherInfoFile=/path/to/file
 *         --ballonNormalizedOutputFile=/path/to/file
 */
public class WeatherBalloonStatistics {
	private static final String START_ACCESS_LOG_ARGUMENT = "balloonWeatherInfoFile";
	private static final String START_OUTPUT_FILE_ARGUMENT = "ballonNormalizedOutputFile";
	private static final CharSequence START_COMMAND_ARGUMENT = "command";
	private static final CharSequence START_GENERATE_DATA_ARGUMENT = "generate-data";
	private static final String START_DISTANCE_UNIT_ARGUMENT = "distance-unit";
	private static final String START_TEMPERATURE_UNIT_ARGUMENT = "temperature-unit";
	private static final Map<String, String> COMMAND_MAP = new HashMap<>();
	private static final String FAKE_DATA_PERCENTAGE = "fakeDataPercentage";

	static {
		COMMAND_MAP.put("meanTemp", "The mean temperature");
		COMMAND_MAP.put("maxTemp", "The maximum temperature");
		COMMAND_MAP.put("minTemp", "The minimum temperature");
		COMMAND_MAP.put("observations", "The number of observations from each observatory");
		COMMAND_MAP.put("totalDistance", "The total distance travelled");
		COMMAND_MAP.put("generate-data", "Generate a file with");
	}


	public static void main(String[] args) {
		BalloonWeatherParserService ballonService = new BalloonWeatherParserServiceImpl();
		boolean isAccessLog = false;
		boolean isAtLeastOneCommand = false;
		boolean isGenerateFileData = false;
		boolean isOutPutFile = false;
		
		String accessLogPath = null;
		String outputFileNormalized = null;
		String fileDataPath = null;
		int percentage = 10;
		//default values
		String distanceUnit = Constants.DISTANCE_UNIT_METER;
		String tempUnit = Constants.TEMPERATURE_UNIT_FAHRENHEIT;
		Set<String> setCommands = new HashSet<>();
		
		
		try {
			for (String arg : args) {
				if (arg.trim().contains(START_ACCESS_LOG_ARGUMENT)) {
					accessLogPath = getValue(arg.trim());
					isAccessLog = true;
				} else if (arg.trim().contains(START_COMMAND_ARGUMENT)) {
					String rawcommands = getValue(arg.trim());
					setCommands.addAll(Arrays.asList(rawcommands.split(Constants.COMMA)));
					verifyCommands(setCommands);
					isAtLeastOneCommand = true;
				}else if(arg.trim().contains(START_GENERATE_DATA_ARGUMENT)) {
					fileDataPath = getValue(arg.trim());
					isGenerateFileData = true;
				}else if(arg.trim().contains(FAKE_DATA_PERCENTAGE)) {
					String sP = getValue(arg.trim());
					try {
						percentage = Integer.parseInt(sP);
					} catch (NumberFormatException e) {
						percentage = 10;
					}
				}else if(arg.trim().contains(START_DISTANCE_UNIT_ARGUMENT)) {
					distanceUnit =  getValue(arg.trim());
					if(distanceUnit != null && 
							(!distanceUnit.equalsIgnoreCase(Constants.DISTANCE_UNIT_METER) ||
							 !distanceUnit.equalsIgnoreCase(Constants.DISTANCE_UNIT_KM) ||
							 !distanceUnit.equalsIgnoreCase(Constants.DISTANCE_UNIT_MILES)
					)) {
						System.out.println("Default Distance unit: m");
						distanceUnit = Constants.DISTANCE_UNIT_METER;
					}
				}else if(arg.trim().contains(START_TEMPERATURE_UNIT_ARGUMENT)) {
					tempUnit = getValue(arg.trim());
					if(tempUnit != null && 
							(!tempUnit.equalsIgnoreCase(Constants.TEMPERATURE_UNIT_CELSISUS) ||
							 !tempUnit.equalsIgnoreCase(Constants.TEMPERATURE_UNIT_FAHRENHEIT) ||
							 !tempUnit.equalsIgnoreCase(Constants.TEMPERATURE_UNIT_KELVIN))) {
						System.out.println("Default temperature unit: celsius");
						tempUnit = Constants.TEMPERATURE_UNIT_CELSISUS;
					}
				}else if(arg.trim().contains(START_OUTPUT_FILE_ARGUMENT)) {
					outputFileNormalized = getValue(arg.trim());
					isOutPutFile = true;
				}
			}

			if (isGenerateFileData) {
				ballonService.generateData(fileDataPath, 500000, percentage);
			} else if(isAccessLog && isAtLeastOneCommand && isOutPutFile){
				Statistics s = ballonService.calculateAndGenerateOutput(accessLogPath, outputFileNormalized, distanceUnit, tempUnit);
				for(String command : setCommands) {
					if(command.equalsIgnoreCase("meanTemp")) {
						System.out.println("Mean Temperature in [" + tempUnit + "] : " + s.getMeanTemperature() );
					}else if(command.equalsIgnoreCase("maxTemp")) {
						System.out.println("MAX Temperature in [" + tempUnit + "] : " + s.getMaxTemperature() );
					}else if(command.equalsIgnoreCase("minTemp")) {
						System.out.println("MIN Temperature in [" + tempUnit + "] : " + s.getMinTemperature() );
					}else if(command.equalsIgnoreCase("observations")) {
						System.out.println("Observatios: " + s.getObservationsByObservatory());
					}else if(command.equalsIgnoreCase("totalDistance")) {
						System.out.println("Total Distance: " + s.getTotalDistanceTravelled());
					}
					
				}
			}else {
				System.out.println("More arguments needed, these are required: [balloonWeatherInfoFile, command and ballonNormalizedOutputFile");
				System.exit(0);
			}
		} catch (CommandNotFoundException e) {
			System.out.println(e.getMessage() + "[meanTemp],[maxTemp],[minTemp],[observations],[totalDistance],[ALL]");
		}
	}

	private static void verifyCommands(Set<String> commands) throws CommandNotFoundException {
		Iterator<String> commandIt = commands.iterator();
		while (commandIt.hasNext()) {
			String command = commandIt.next();
			if (!COMMAND_MAP.containsKey(command)) {
				commands.remove(command);
			}
		}
		if (commands.isEmpty()) {
			throw new CommandNotFoundException(
					"The commands sent in the request do not match to any command registered in this tool: ");
		}
	}

	/**
	 * 
	 * @param pair
	 * @return the value of th argument
	 */
	private static String getValue(String pair) {
		String[] vals = pair.split("=");
		return vals[1];
	}
}
