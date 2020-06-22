package main.java.com.jpmc.messageprocessor.factory;

import java.util.HashMap;
import java.util.Map;

import main.java.com.jpmc.messageprocessor.model.SaleType;
import main.java.com.jpmc.messageprocessor.service.SalesNotificationHandler;
import main.java.com.jpmc.messageprocessor.service.impl.SingleSaleNotificationHandler;
import main.java.com.jpmc.messageprocessor.service.impl.MultiSaleNotificationHandler;
import main.java.com.jpmc.messageprocessor.service.impl.AdjustmentHandler;

public class SalesNotificationHandlerFactory {
    private static Map<SaleType, SalesNotificationHandler> salesNotificationHandler;

    static {
    	initSalesNotificationHandler();
    }

    private static void initSalesNotificationHandler() {
    	salesNotificationHandler = new HashMap<SaleType, SalesNotificationHandler>();
    	salesNotificationHandler.put(SaleType.SINGLE_SALE, new SingleSaleNotificationHandler());
    	salesNotificationHandler.put(SaleType.MULTI_SALE, new MultiSaleNotificationHandler());
    	salesNotificationHandler.put(SaleType.ADJUSTMENT, new AdjustmentHandler());
    }
    
    public static SalesNotificationHandler getSalesNotificationHandler(SaleType saleType) {
        final SalesNotificationHandler notificationHandler = salesNotificationHandler.get(saleType);
        if (notificationHandler == null) {
            System.err.println("Ignoring the unrecognized sales type notification. " + saleType);
        }
        return notificationHandler;
    }

    
}