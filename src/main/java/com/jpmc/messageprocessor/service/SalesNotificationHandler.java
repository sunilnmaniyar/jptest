package main.java.com.jpmc.messageprocessor.service;

import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;

public interface SalesNotificationHandler {

	void handleSalesNotification(SalesNotification salesNotification) throws SalesNotificationHandlerException;
}
