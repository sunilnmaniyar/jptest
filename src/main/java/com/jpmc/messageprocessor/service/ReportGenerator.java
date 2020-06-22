package main.java.com.jpmc.messageprocessor.service;

import java.util.List;

import main.java.com.jpmc.messageprocessor.model.Adjustment;
import main.java.com.jpmc.messageprocessor.model.Sale;

public interface ReportGenerator {
	String generateSalesReport(List<Sale> sales);
    String generateAdjustmentsReport(List<Adjustment> adjustments);
}
