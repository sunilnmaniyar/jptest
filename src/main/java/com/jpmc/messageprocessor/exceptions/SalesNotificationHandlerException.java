package main.java.com.jpmc.messageprocessor.exceptions;

public class SalesNotificationHandlerException extends Exception {

	private static final long serialVersionUID = 1L;

	public SalesNotificationHandlerException(String message) {
        super(message);
    }

    public SalesNotificationHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}