package main.java.com.jpmc.messageprocessor.service.impl;

import main.java.com.jpmc.messageprocessor.exceptions.EmptySalesNotificationQueueException;
import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.factory.SalesNotificationHandlerFactory;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.service.SalesNotificationHandler;
import main.java.com.jpmc.messageprocessor.service.NotificationProcessor;
import main.java.com.jpmc.messageprocessor.service.ReportGenerator;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

public class SalesNotificationProcessor implements NotificationProcessor {
    private final ReportGenerator reportGenerator;

    public SalesNotificationProcessor(final ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @Override
    public void startProcessing() throws InterruptedException {
        System.out.println("Sales notification processor has started.\n");

        int salesNotificationsProcessed = 0;
        while (SalesNotificationStore.hasNextNotification()) {
        	SalesNotification salesNotification;
            try {
            	salesNotification = SalesNotificationStore.nextNotification();
            } catch (EmptySalesNotificationQueueException e) {
                System.err.println(e.getMessage());
                return;
            }

            boolean success = processSalesNotification(salesNotification);
            if (!success) {
                continue;
            }
            salesNotificationsProcessed++;

            if (salesNotificationsProcessed % 10 == 0) {
                String salesReport = reportGenerator.generateSalesReport(SalesNotificationStore.totalSales);
                System.out.println(salesReport);
            }
            if (salesNotificationsProcessed %50 == 0) {   
            	System.out.println("The applicationg is pausing and will stop processing new notifications while adjustment report gets generated.\n");
            	Thread.sleep(5000);
				String adjustmentsReport = reportGenerator.generateAdjustmentsReport(SalesNotificationStore.totalAdjustments);
                System.out.println(adjustmentsReport);
                System.out.println("The application is starting again to process new notifications.\n");
                Thread.sleep(5000);
			}
                           
        }
        System.out.println("Sales notification processor finished.");
    }

    private boolean processSalesNotification(SalesNotification salesNotification) {
    	SalesNotificationHandler salesNotificationHandler = SalesNotificationHandlerFactory.getSalesNotificationHandler(salesNotification.getSaleType());
        if (salesNotificationHandler == null) {
            System.err.println("Error while getting SalesNotificationHandler for type=" + salesNotification.getSaleType());
            return false;
        }

        try {
        	salesNotificationHandler.handleSalesNotification(salesNotification);
        } catch (SalesNotificationHandlerException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
