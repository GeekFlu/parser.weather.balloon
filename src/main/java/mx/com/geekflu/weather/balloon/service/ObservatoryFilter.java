package mx.com.geekflu.weather.balloon.service;

import java.util.List;
import java.util.stream.Stream;

import mx.com.geekflu.weather.balloon.model.DataRow;

public class ObservatoryFilter implements Filter<DataRow> {

	@Override
	public Stream<DataRow> filter(List<DataRow> itmes, Specification<DataRow> spec) {
		return itmes.stream().filter(d -> spec.isSatisfied(d));
	}

}
