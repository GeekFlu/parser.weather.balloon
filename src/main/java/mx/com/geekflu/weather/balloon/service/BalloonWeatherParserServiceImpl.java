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
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import mx.com.geekflu.weather.balloon.model.DataRow;
import mx.com.geekflu.weather.balloon.model.Statistics;
import mx.com.geekflu.weather.balloon.util.Constants;
import mx.com.geekflu.weather.balloon.util.Converters;

/**
 * 
 * @author Luis E. Gonzalez
 *
 */
public class BalloonWeatherParserServiceImpl extends Converters implements BalloonWeatherParserService  {

	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private DataRow minTemp;
	private DataRow maxTemp;
	private Double currentTempSum = 0.0;
	private Double currentDistanceSum = 0.0;

	@Override
	public Statistics calculateAndGenerateOutput(String filePath, String distanceUnit, String temperatureUnit) {
		FileInputStream inputStream = null;
		Scanner sc = null;
		Statistics st = new Statistics();
		try {
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, StandardCharsets.UTF_8.name());
			List<DataRow> dataBlock = new ArrayList<>();
			int counter = 0;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				dataBlock.add(getRow(line));
				if(dataBlock.size() > Constants.DEFAULT_SIZE_BLOCK) {
					List<DataRow> convertedDataBlock = convertAndMergeData(dataBlock, temperatureUnit);
					applyRules(convertedDataBlock, st, distanceUnit);
					writeDate2File(filePath, convertedDataBlock);
					counter += convertedDataBlock.size();
					dataBlock.clear();
				}
			}
			if(dataBlock.size() > 0) {
				List<DataRow> convertedDataBlock = convertAndMergeData(dataBlock, temperatureUnit);
				applyRules(convertedDataBlock, st, distanceUnit);
				writeDate2File(filePath, convertedDataBlock);
				counter += convertedDataBlock.size();
				dataBlock.clear();
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
			st.setMaxTemperature(this.maxTemp.getTemperature());
			st.setMinTemperature(this.minTemp.getTemperature());
			st.setMeanTemperature((float) (this.currentTempSum / counter));
			st.setTotalDistanceTravelled(this.currentDistanceSum);
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
		return st;
	}

	/**
	 * <timestamp>|<location>|<temperature>|<observatory>
	 * @param filePath
	 * @param convertedDataBlock
	 */
	private void writeDate2File(String filePath, List<DataRow> convertedDataBlock) {
		Writer fstream = null;
		try {
			fstream = new OutputStreamWriter(new FileOutputStream(filePath.concat(".out"), true), StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fstream);
			convertedDataBlock.stream().forEach(row -> {
				String f = "%s|%d,%d|%f|%s";
				try {
					bw.write(String.format(f, row.getTimeStamp(), row.getX(), row.getY(), row.getTemperature(), row.getObservatory()));
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void applyRules(List<DataRow> convertedDataBlock, Statistics st, String distanceUnit) {
		updateCurrentMinTemp(convertedDataBlock);
		updateCurrentMaxTemp(convertedDataBlock);
		updateCurrentTempSum(convertedDataBlock);
		calculateDistanceBetweenPoint(convertedDataBlock, distanceUnit);
		countObservationsByObs(convertedDataBlock, st);
	}


	private void updateCurrentTempSum(List<DataRow> dataBlock) {
		this.currentTempSum += dataBlock.stream().mapToDouble(row -> row.getTemperature()).sum();
	}


	/**
	 * Calculate the observations by observatory
	 * @param dataBlock
	 * @param st
	 */
	private void countObservationsByObs(List<DataRow> dataBlock, Statistics st) {
		for(DataRow r : dataBlock) {
			if(r.getObservatory() != null) {
				String o = r.getObservatory().trim();
				if(o.equalsIgnoreCase(Constants.OBSERVATORY_AU)) {
					st.getObservationsByObservatory()
						.put(Constants.OBSERVATORY_AU, st.getObservationsByObservatory().get(Constants.OBSERVATORY_AU) + 1);
				}else if(o.equalsIgnoreCase(Constants.OBSERVATORY_FR)) {
					st.getObservationsByObservatory()
					.put(Constants.OBSERVATORY_FR, st.getObservationsByObservatory().get(Constants.OBSERVATORY_FR) + 1);
				}else if(o.equalsIgnoreCase(Constants.OBSERVATORY_US)) {
					st.getObservationsByObservatory()
					.put(Constants.OBSERVATORY_US, st.getObservationsByObservatory().get(Constants.OBSERVATORY_US) + 1);
				}else {
					st.getObservationsByObservatory()
					.put(Constants.OBSERVATORY_OTHERS, st.getObservationsByObservatory().get(Constants.OBSERVATORY_OTHERS) + 1);
				}
			}
		}
	}

	private void calculateDistanceBetweenPoint(List<DataRow> dataBlock, String distanceUnit) {
		for(int i = 0; i < dataBlock.size() - 1 ; i++) {
			DataRow p2 = dataBlock.get(i + 1);
			DataRow p1 = dataBlock.get(i);
			Double distance = Math.sqrt(
					Math.pow((p2.getX() - p1.getX()) , 2) + Math.pow((p2.getY() - p1.getY()), 2)
			);
			this.currentDistanceSum += distance;
		}
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
		
		String[] coors = data[1].split(",");
		d.setX(Integer.parseInt(coors[0]));
		d.setY(Integer.parseInt(coors[1]));
		
		d.setTemperature(Float.parseFloat(data[2]));
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

	private void updateCurrentMaxTemp(List<DataRow> dataBlock) {
		DataRow rMax = dataBlock.stream().max(Comparator.comparing(DataRow::getTemperature)).orElse(null);
		if(this.maxTemp != null &&
				rMax != null &&
				rMax.getTemperature() > this.maxTemp.getTemperature()) {
			this.maxTemp = rMax;
		}else {
			this.maxTemp = rMax;
		}
	}
	
	private List<DataRow> convertAndMergeData(List<DataRow> dataBlock, String temperatureUnit) {
		List<DataRow> obsAU = dataBlock.stream().filter(row -> row.getObservatory().equalsIgnoreCase(Constants.OBSERVATORY_AU)).collect(Collectors.toList());
		List<DataRow> obsUS = dataBlock.stream().filter(row -> row.getObservatory().equalsIgnoreCase(Constants.OBSERVATORY_US)).collect(Collectors.toList());
		List<DataRow> obsFR = dataBlock.stream().filter(row -> row.getObservatory().equalsIgnoreCase(Constants.OBSERVATORY_FR)).collect(Collectors.toList());
		List<DataRow> obsOThers = dataBlock.stream().filter(row -> row.getObservatory().equalsIgnoreCase(Constants.OBSERVATORY_OTHERS)).collect(Collectors.toList());
		//Convert to Kelvin AU c -> K y US F->K
		if(temperatureUnit.equalsIgnoreCase(Constants.TEMPERATURE_UNIT_KELVIN)) {
			obsAU.stream().forEach(convertFromCelsiusToKelvin);
			obsUS.stream().forEach(convertFromFahrenheitToKelvin);
		}else if(temperatureUnit.equalsIgnoreCase(Constants.TEMPERATURE_UNIT_CELSISUS)) {//Convert us F -> C y FR K -> C
			obsUS.stream().forEach(convertFromFahrenheitToCelsius);
			obsFR.stream().forEach(convertFromKelvinToCelsius);
			obsOThers.stream().forEach(convertFromKelvinToCelsius);
		}else if(temperatureUnit.equalsIgnoreCase(Constants.TEMPERATURE_UNIT_FAHRENHEIT)) {
			obsAU.stream().forEach(convertFromCelsiusToFahrenheit);
			obsFR.stream().forEach(convertFromKelvinToFahrenheit);
			obsOThers.stream().forEach(convertFromKelvinToFahrenheit);
		}
		List<DataRow> convertedData = new ArrayList<>();
		convertedData.addAll(obsAU);
		convertedData.addAll(obsUS);
		convertedData.addAll(obsFR);
		convertedData.addAll(obsOThers);
		return convertedData;
	}


	private void updateCurrentMinTemp(List<DataRow> dataBlock) {
		DataRow rMin = dataBlock.stream().min(Comparator.comparing(DataRow::getTemperature)).orElse(null);
		if(this.minTemp != null &&
				rMin != null &&
				rMin.getTemperature() < this.minTemp.getTemperature()) {
			this.minTemp = rMin;
		}else {
			this.minTemp = rMin;
		}
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
