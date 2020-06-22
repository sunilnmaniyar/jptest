package test.java.com.jpmc.messageprocessor;

import main.java.com.jpmc.messageprocessor.exceptions.SalesNotificationHandlerException;
import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.AdjustmentType;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.SaleType;
import main.java.com.jpmc.messageprocessor.model.Sale;
import main.java.com.jpmc.messageprocessor.service.impl.SingleSaleNotificationHandler;
import main.java.com.jpmc.messageprocessor.store.SalesNotificationStore;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SingleSaleNotificationHandlerTest {
    private SingleSaleNotificationHandler singleSaleHandler;
    private List<Sale> salesNotifications;
    private Sale validSale = new Sale("Apple", BigDecimal.valueOf(0.60), 1);
    private Sale inValidSale = new Sale("Orange", BigDecimal.valueOf(0.30), 2);

    @Before
    public void setUp() {
    	singleSaleHandler = new SingleSaleNotificationHandler();
    	salesNotifications = new ArrayList<>();
    	salesNotifications.add(validSale);
    	salesNotifications.add(inValidSale);
        SalesNotificationStore.totalSales = salesNotifications;
    }

    @Test
    public void handleValidNotification() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.SINGLE_SALE, validSale);
    	singleSaleHandler.handleSalesNotification(salesNotification);
        assertEquals(validSale, SalesNotificationStore.totalSales.get(0));
    }
    
    @Test(expected = SalesNotificationHandlerException.class)
    public void handleNotificationWithUnitSize2() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.SINGLE_SALE, inValidSale);
    	singleSaleHandler.handleSalesNotification(salesNotification);
    	assertEquals(inValidSale, SalesNotificationStore.totalSales.get(1));
    }
    
    @Test(expected = SalesNotificationHandlerException.class)
    public void handleInvalidNotification() throws Exception {
    	SalesNotification salesNotification = new SalesNotification(SaleType.SINGLE_SALE, new Adjustment(AdjustmentType.MULTIPLY,
                "Apple", BigDecimal.valueOf(2)));
    	singleSaleHandler.handleSalesNotification(salesNotification);
    }    

}