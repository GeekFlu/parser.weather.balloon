package mx.com.geekflu.weather.balloon.service;

import mx.com.geekflu.weather.balloon.model.DataRow;
import mx.com.geekflu.weather.balloon.model.ObervatoryEnum;

public class ObservatorySpecification implements Specification<DataRow>{
	private ObervatoryEnum observatory;
	
	public ObservatorySpecification(ObervatoryEnum observatory) {
		super();
		this.observatory = observatory;
	}

	@Override
	public boolean isSatisfied(DataRow item) {
		return item.getObservatory() == this.observatory;
	}


}
