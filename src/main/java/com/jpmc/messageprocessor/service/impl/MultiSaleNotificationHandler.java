package main.java.com.jpmc.messageprocessor.service.impl;

import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.Sale;
import main.java.com.jpmc.messageprocessor.service.SalesNotificationHandler;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

public class MultiSaleNotificationHandler implements SalesNotificationHandler {

    @Override
    public void handleSalesNotification(SalesNotification salesNotification) throws SalesNotificationHandlerException {
        if (!(salesNotification.getDetails() instanceof Sale)) {
            throw new SalesNotificationHandlerException("Notification is not of type MultiSale.");
        }

        Sale sale = (Sale) salesNotification.getDetails();
        if (sale.getTotalUnits() == 1) {
            throw new SalesNotificationHandlerException("Notification has 1 unit to sell. It should be a SingleSale notification.");
        }
        SalesNotificationStore.addSale(sale);
    }
}
