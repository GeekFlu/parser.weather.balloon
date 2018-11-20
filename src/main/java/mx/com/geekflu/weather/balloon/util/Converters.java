package mx.com.geekflu.weather.balloon.util;

import java.util.function.Consumer;

import mx.com.geekflu.weather.balloon.model.DataRow;

public class Converters {
	
	public Consumer<DataRow> convertFromCelsiusToKelvin = new Consumer<DataRow>() {
		@Override
		public void accept(DataRow t) {
			t.setTemperature(t.getTemperature() + Constants.KELVIN);
		}
	};

	public Consumer<DataRow> convertFromCelsiusToFahrenheit = new Consumer<DataRow>() {
		@Override
		public void accept(DataRow t) {
			t.setTemperature((float) (t.getTemperature() * 1.8 + 32f));
		}
	};

	public Consumer<DataRow> convertFromFahrenheitToCelsius = new Consumer<DataRow>() {
		@Override
		public void accept(DataRow t) {
			t.setTemperature((float) ((t.getTemperature() - 32f) / 1.8));
		}
	};
	
	public Consumer<DataRow> convertFromFahrenheitToKelvin = new Consumer<DataRow>() {
		@Override
		public void accept(DataRow t) {
			t.setTemperature((t.getTemperature() + 459.67f) * 5/9);
		}
	};
	
	public Consumer<DataRow> convertFromKelvinToCelsius = new Consumer<DataRow>() {
		@Override
		public void accept(DataRow t) {
			t.setTemperature(t.getTemperature() - 273.15f);
		}
	};
	
	public Consumer<DataRow> convertFromKelvinToFahrenheit = new Consumer<DataRow>() {
		@Override
		public void accept(DataRow t) {
			t.setTemperature((t.getTemperature() * 9/5) - 459.67f);
		}
	};
}
