package main.java.com.jpmc.messageprocessor.model;

import java.math.BigDecimal;

public class Adjustment {
    private AdjustmentType type;
    private String productName;
    private BigDecimal amount;

    public Adjustment(AdjustmentType type, String productName, BigDecimal amount) {
        this.type = type;
        this.productName = productName;
        this.amount = amount;
    }

    public AdjustmentType getType() {
        return type;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adjustment that = (Adjustment) o;

        if (type != that.type) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
        int hashCode = type != null ? type.hashCode() : 0;
        hashCode = (int) (prime * hashCode + (productName != null ? productName.hashCode() : 0));
        hashCode = (int) (prime * hashCode + (amount != null ? amount.hashCode() : 0));
        return hashCode;
    }

    @Override
    public String toString() {
        return "Adjustment{" +
                "type=" + type +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                '}';
    }
}