package main.java.com.jpmc.messageprocessor.service.impl;

import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.Sale;
import main.java.com.jpmc.messageprocessor.service.SalesNotificationHandler;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

public class SingleSaleNotificationHandler implements SalesNotificationHandler {

    @Override
    public void handleSalesNotification(SalesNotification salesNotification) throws SalesNotificationHandlerException {
        if (!(salesNotification.getDetails() instanceof Sale)) {
            throw new SalesNotificationHandlerException("Notification for SingleSale is not of type SingleSale");
        }

        Sale sale = (Sale) salesNotification.getDetails();
        if (sale.getTotalUnits() != 1) {
            throw new SalesNotificationHandlerException("Notification has incorrect units = " + sale.getTotalUnits());
        }

        SalesNotificationStore.addSale(sale);
    }
}
