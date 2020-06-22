package main.java.com.jpmc.messageprocessor;

import main.java.com.jpmc.messageprocessor.factory.SalesNotificationGenerator;
import main.java.com.jpmc.messageprocessor.service.NotificationProcessor;
import main.java.com.jpmc.messageprocessor.service.ReportGenerator;
import main.java.com.jpmc.messageprocessor.service.impl.SalesNotificationProcessor;
import main.java.com.jpmc.messageprocessor.service.impl.SalesReportGenerator;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

public class MessageProcessor {

    public static void main(String[] args) throws InterruptedException{        
    	ReportGenerator reportGenerator = new SalesReportGenerator();
    	NotificationProcessor notificationProcessor = new SalesNotificationProcessor(reportGenerator);

        SalesNotificationGenerator salesNotificationGenerator = new SalesNotificationGenerator();
        SalesNotificationStore.salesNotificationsQueue.addAll(salesNotificationGenerator.generateSalesNotifications());
        try{
        	notificationProcessor.startProcessing();
        }catch(InterruptedException interruptedException){
        	System.out.println("Notification processor interrupted. Exiting the application now.");
        }
        
    }
}

