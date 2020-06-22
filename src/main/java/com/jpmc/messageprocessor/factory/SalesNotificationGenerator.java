package main.java.com.jpmc.messageprocessor.factory;

import java.math.BigDecimal;
import java.util.*;

import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.AdjustmentType;
import main.java.com.jpmc.messageprocessor.model.SalesNotification;
import main.java.com.jpmc.messageprocessor.model.SaleType;
import main.java.com.jpmc.messageprocessor.model.Sale;

public class SalesNotificationGenerator {
	
	    Set<String> products = new HashSet<String>(Arrays.asList("Apple", "Mango", "Orange", "Kiwi", "Banana"));
	    Set<Integer> units = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
	    Set<AdjustmentType> adjustmentTypes = new HashSet<AdjustmentType>(Arrays.asList(AdjustmentType.ADD, AdjustmentType.SUBTRACT, AdjustmentType.MULTIPLY));
	    Set<BigDecimal> adjustmentPrices = new HashSet<BigDecimal>(Arrays.asList(BigDecimal.valueOf(0.05),
	            BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.15)));

	    Map<String, BigDecimal> productPrices = new HashMap<String, BigDecimal>(){
			private static final long serialVersionUID = 1L;
			{
	        put("Apple", BigDecimal.valueOf(0.60));
	        put("Mango", BigDecimal.valueOf(0.90));
	        put("Orange", BigDecimal.valueOf(0.30));
	        put("Kiwi", BigDecimal.valueOf(0.40));
	        put("Banana", BigDecimal.valueOf(0.20));}};
	     
	    public List<SalesNotification> generateSalesNotifications() {
	        List<SalesNotification> salesNotifications = new LinkedList<SalesNotification>();

	        for (int i = 1; i <= 120; i++) {
	        	if (i % 15 == 0) {
	                String product = (String) getRandom(products);
	                salesNotifications.add(new SalesNotification(SaleType.MULTI_SALE,
	                        new Sale(product, productPrices.get(product),(Integer) getRandom(units))));
	            } else if (i % 10 == 0) {
	            	salesNotifications.add(new SalesNotification(SaleType.ADJUSTMENT,
	                        new Adjustment((AdjustmentType) getRandom(adjustmentTypes),
	                                (String) getRandom(products),(BigDecimal) getRandom(adjustmentPrices))));
	            } else {
	                String product = (String) getRandom(products);
	                salesNotifications.add(new SalesNotification(SaleType.SINGLE_SALE,
	                        new Sale(product, productPrices.get(product),1)));
	            }
	        }
	        return salesNotifications;
	    }

	    private Object getRandom(Collection collection) {
	        return collection.stream()
	                .skip((int) (collection.size() * Math.random()))
	                .findFirst().get();
	    }	    
}
