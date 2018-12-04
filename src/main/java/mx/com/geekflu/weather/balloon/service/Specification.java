package mx.com.geekflu.weather.balloon.service;

public interface Specification<T> {

	boolean isSatisfied(T item);
	
}
