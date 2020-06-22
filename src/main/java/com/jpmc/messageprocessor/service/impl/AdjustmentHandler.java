package main.java.com.jpmc.messageprocessor.service.impl;

import java.math.BigDecimal;

import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.AdjustmentType;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.Sale;
import main.java.com.jpmc.messageprocessor.service.SalesNotificationHandler;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

public class AdjustmentHandler implements SalesNotificationHandler {
    @Override
    public void handleSalesNotification(SalesNotification salesNotification) throws SalesNotificationHandlerException {
        if (!(salesNotification.getDetails() instanceof Adjustment)) {
            throw new SalesNotificationHandlerException("Notification is not of type Adjustment");
        }
        Adjustment adjustment = (Adjustment) salesNotification.getDetails();
        processAdjustment(adjustment);
        SalesNotificationStore.addAdjustment(adjustment);
    }

    private void processAdjustment(Adjustment adjustment) {
        for (Sale sale : SalesNotificationStore.totalSales) {
            if (sale.getProductName().equals(adjustment.getProductName())) {
                if (adjustment.getType() == AdjustmentType.ADD) {
                    sale.setUnitPrice(sale.getUnitPrice().add(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentType.SUBTRACT) {
                    BigDecimal newPrice = sale.getUnitPrice().subtract(adjustment.getAmount());
                    newPrice = newPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newPrice;
                    sale.setUnitPrice(newPrice);
                } else if (adjustment.getType() == AdjustmentType.MULTIPLY) {
                    sale.setUnitPrice(sale.getUnitPrice().multiply(adjustment.getAmount()));
                } 
            }
        }
    }
}
