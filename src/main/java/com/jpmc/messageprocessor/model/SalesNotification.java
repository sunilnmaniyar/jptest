package main.java.com.jpmc.messageprocessor.model;

public class SalesNotification {
    private SaleType saleType;
    private Object details;

    public SalesNotification(SaleType saleType, Object details) {
        this.saleType = saleType;
        this.details = details;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public Object getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesNotification salesNotification = (SalesNotification) o;

        if (saleType != salesNotification.saleType) return false;
        return details != null ? details.equals(salesNotification.details) : salesNotification.details == null;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
        int hashCode = saleType != null ? saleType.hashCode() : 0;
        hashCode = (int) (prime * hashCode + (details != null ? details.hashCode() : 0));
        return hashCode;
    }

    @Override
    public String toString() {
        return "SalesNotification{" +
                "messageType=" + saleType +
                ", details=" + details +
                '}';
    }
}