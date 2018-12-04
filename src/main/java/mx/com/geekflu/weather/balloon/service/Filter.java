package mx.com.geekflu.weather.balloon.service;

import java.util.List;
import java.util.stream.Stream;

public interface Filter<T> {
	Stream<T> filter(List<T> itmes, Specification<T> spec);
}
