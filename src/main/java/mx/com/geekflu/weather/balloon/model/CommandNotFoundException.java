package mx.com.geekflu.weather.balloon.model;

public class CommandNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public CommandNotFoundException() {
		
	}
	
	public CommandNotFoundException(String message) {
		super(message);
	}
	
}
