package mx.com.geekflu.weather.balloon.service;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mx.com.geekflu.weather.balloon.model.DataRow;
import mx.com.geekflu.weather.balloon.model.Statistics;
import mx.com.geekflu.weather.balloon.util.Constants;

/**
 * 
 * @author Luis E. Gonzalez
 *
 */
public class BalloonWeatherParserServiceImpl implements BalloonWeatherParserService {

	private ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Override
	public Statistics calculateAndGenerateOutput(String filePath, String distanceUnit, String temperatureUnit) {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, StandardCharsets.UTF_8.name());
			List<DataRow> dataBlock = new ArrayList<>();
			Statistics st = new Statistics();
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				dataBlock.add(getRow(line));
				if(dataBlock.size() > Constants.DEFAULT_SIZE_BLOCK) {
					Integer min = getMin(dataBlock);
					Integer max = getMax(dataBlock);
				}
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (sc != null) {
				sc.close();
			}
		}
		return null;
	}

	/**
	 * @author Luis E. Gonzalez <timestamp>|<location>|<temperature>|<observatory>
	 * @param line
	 * @return
	 */
	private DataRow getRow(String line) {
		String[] data = line.split("\\|");
		DataRow d = new DataRow();
		d.setTimeStamp(data[0]);
		d.setLocation(data[1]);
		d.setTemperature(data[2]);
		d.setObservatory(data[3]);
		return d;
	}

	@Override
	public void generateData(String filePath, int numLines2Generate, int fakeDataPercentage) {
		try {
			Writer fstream = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fstream);
			for (Future<List<String>> f : executorService
					.invokeAll(splitDataInWorkers(numLines2Generate, fakeDataPercentage))) {
				List<String> ls = f.get();
				ls.stream().forEach(line -> {
					try {
						bw.write(line);
						bw.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			bw.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private List<GeneratorWorker> splitDataInWorkers(int numLines2Generate, int fakeData) {
		List<GeneratorWorker> l = new ArrayList<>();
		int c = numLines2Generate / 5;
		int reminder = numLines2Generate % 5;
		for (int i = 0; i < 5; i++) {
			l.add(new GeneratorWorker(c));
		}
		if (reminder > 0) {
			l.add(new GeneratorWorker(reminder));
		}
		return l;
	}

	private Integer getMax(List<String> temps) {
		return temps.stream().mapToInt(number -> Integer.parseInt(number)).max().orElse(Integer.MIN_VALUE);
	}
	
	private Integer getMin(List<String> temps) {
		return temps.stream().mapToInt(number -> Integer.parseInt(number)).min().orElse(Integer.MAX_VALUE);
	}

}



/**
 * 
 * @author Luis E. Gonzalez <timestamp>|<location>|<temperature>|<observatory>
 *         Where the timestamp is yyyy-MM-ddThh:mm in UTC. Where the location is
 *         a co-ordinate x,y. And x, and y are natural numbers in observatory
 *         specific units. Where the temperature is an integer representing
 *         temperature in observatory specific units. Where the observatory is a
 *         code indicating where the measurements were relayed from.
 * 
 */
class GeneratorWorker implements Callable<List<String>> {
	private int numGen;

	public GeneratorWorker(int numGen) {
		super();
		this.numGen = numGen;
	}

	@Override
	public List<String> call() throws Exception {
		List<String> lst = new ArrayList<>();

		for (int i = 0; i < numGen; i++) {
			int x = Constants.getRandomNumberInRange(Constants.MIN, Constants.MAX);
			int y = Constants.getRandomNumberInRange(Constants.MIN, Constants.MAX);
			LocalDateTime randomDate = LocalDateTime.of(Constants.createRandomDate(1990, 2018),
					Constants.createRandomTime());
			String dateTimeF = randomDate.format(Constants.formatter);
			int temperature = Constants.getRandomNumberInRange(-50, 60);
			String observatory = Constants.OBSERVATORIES_NAME[Constants.getRandomNumberInRange(0, 3)];
			lst.add(String.join(Constants.PIPE, dateTimeF,
					String.valueOf(x).concat(Constants.COMMA).concat(String.valueOf(y)), String.valueOf(temperature),
					observatory));
		}
		return lst;
	}
}
