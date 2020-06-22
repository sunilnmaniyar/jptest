package main.java.com.jpmc.messageprocessor.exceptions;

public class EmptySalesNotificationQueueException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public EmptySalesNotificationQueueException(String message) {
        super(message);
    }

    public EmptySalesNotificationQueueException(String message, Throwable cause) {
        super(message, cause);
    }
}