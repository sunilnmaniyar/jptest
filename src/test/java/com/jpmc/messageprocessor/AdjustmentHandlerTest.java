package test.java.com.jpmc.messageprocessor;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.AdjustmentType;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.SaleType;
import main.java.com.jpmc.messageprocessor.model.Sale;
import main.java.com.jpmc.messageprocessor.service.impl.AdjustmentHandler;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

public class AdjustmentHandlerTest {

    private AdjustmentHandler adjustmentHandler;
    private List<Sale> salesNotifications;
    private Sale addAdjustment = new Sale("Apple", BigDecimal.valueOf(0.60), 1);
    private Sale substractAdjustment = new Sale("Banana", BigDecimal.valueOf(0.20), 2);
    private Sale multiplyAdjustment = new Sale("Orange", BigDecimal.valueOf(0.30), 1);

    @Before
    public void setUp() {
    	salesNotifications = new ArrayList<>();
    	salesNotifications.add(addAdjustment);
    	salesNotifications.add(substractAdjustment);
    	salesNotifications.add(multiplyAdjustment);
        adjustmentHandler = new AdjustmentHandler();
        SalesNotificationStore.totalSales = salesNotifications;
    }

    @Test
    public void handleNotificationAdd() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.ADJUSTMENT, new Adjustment(AdjustmentType.ADD,
                "Apple", BigDecimal.valueOf(0.30)));

    	adjustmentHandler.handleSalesNotification(salesNotification);
        for (Sale sale : SalesNotificationStore.totalSales) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(0.90), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.30), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void handleNotificationSubtract() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.ADJUSTMENT, new Adjustment(AdjustmentType.SUBTRACT,
                "Banana", BigDecimal.valueOf(0.10)));

    	adjustmentHandler.handleSalesNotification(salesNotification);
        for (Sale sale : SalesNotificationStore.totalSales) {
            if (sale.getProductName().equals("Banana")) {
                assertEquals(BigDecimal.valueOf(0.10), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.30), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void handleNotificationMultiply() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.ADJUSTMENT, new Adjustment(AdjustmentType.MULTIPLY,
                "Orange", BigDecimal.valueOf(2)));

    	adjustmentHandler.handleSalesNotification(salesNotification);
        for (Sale sale : SalesNotificationStore.totalSales) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(0.60), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.60), sale.getUnitPrice());
            }
        }
    }

    @Test(expected = SalesNotificationHandlerException.class)
    public void handleInvalidNotification() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.MULTI_SALE, addAdjustment);
    	adjustmentHandler.handleSalesNotification(salesNotification);
    }

}