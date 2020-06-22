package main.java.com.jpmc.messageprocessor.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import main.java.com.jpmc.messageprocessor.service.ReportGenerator;
import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.Sale;

public class SalesReportGenerator implements ReportGenerator {

    @Override
    public String generateSalesReport(List<Sale> sales) {
        Map<String, BigDecimal> productTotalCost = new HashMap<String, BigDecimal>();
        Map<String, Integer> productTotalCount = new HashMap<String, Integer>();

        BigDecimal total = BigDecimal.ZERO;
        int totalUnitsSold = 0;
        for (Sale sale : sales) {
            if (!productTotalCost.containsKey(sale.getProductName())) {
                BigDecimal productCost = sale.getUnitPrice().multiply(BigDecimal.valueOf(sale.getTotalUnits()));
                productTotalCost.put(sale.getProductName(), productCost);

            } else {
                BigDecimal productCost = productTotalCost.get(sale.getProductName());
                productCost = productCost.add(sale.getUnitPrice().multiply(BigDecimal.valueOf(sale.getTotalUnits())));
                productTotalCost.put(sale.getProductName(), productCost);
            }

            if (!productTotalCount.containsKey(sale.getProductName())) {
            	productTotalCount.put(sale.getProductName(), sale.getTotalUnits());
            } else {
            	productTotalCount.put(sale.getProductName(), productTotalCount.get(sale.getProductName()) + sale.getTotalUnits());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("************************ SALES REPORT ************************\n");
        for (Map.Entry<String, BigDecimal> entry : productTotalCost.entrySet()) {
            totalUnitsSold += productTotalCount.get(entry.getKey());
            sb.append("Product{name= ")
                    .append(entry.getKey())
                    .append(", units= ")
                    .append(productTotalCount.get(entry.getKey()))
                    .append(", amount= £")
                    .append(entry.getValue().toString())
                    .append("}\n");
            total = total.add(entry.getValue());
        }
        sb.append("Total units sold - " + totalUnitsSold)
                .append("\n")
                .append("Total cost - £")
                .append(total.toString())
                .append("\n");

        return sb.toString();
    }

    @Override
    public String generateAdjustmentsReport(List<Adjustment> adjustments) {
        StringBuilder sb = new StringBuilder();
        sb.append("************************ ADJUSTMENTS REPORT ************************\n");
        for (Adjustment adjustment : adjustments) {
            sb.append(adjustment).append("\n");
        }
        return sb.toString();
    }
}