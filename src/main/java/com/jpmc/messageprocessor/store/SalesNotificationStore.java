package main.java.com.jpmc.messageprocessor.store;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.com.jpmc.messageprocessor.exceptions.EmptySalesNotificationQueueException;
import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.Sale;

public class SalesNotificationStore {
	public static Queue<SalesNotification> salesNotificationsQueue = new LinkedList<SalesNotification>();
    public static List<Sale> totalSales = new ArrayList<Sale>();
    public static List<Adjustment> totalAdjustments = new ArrayList<Adjustment>();

    public static SalesNotification nextNotification() throws EmptySalesNotificationQueueException {
        if (salesNotificationsQueue.isEmpty()) {
            throw new EmptySalesNotificationQueueException("The sales notifications queue is empty.");
        }
        return salesNotificationsQueue.poll();
    }

    public static boolean hasNextNotification() {
        return !salesNotificationsQueue.isEmpty();
    }

    public static int totalSales() {
        return totalSales.size();
    }

    public static void addSale(Sale sale) {
    	totalSales.add(sale);
    }

    public static void addAdjustment(Adjustment adjustment) {
    	totalAdjustments.add(adjustment);
    }

}