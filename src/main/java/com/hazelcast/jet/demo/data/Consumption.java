package com.hazelcast.jet.demo.data;

/**
 * date: 9/29/17
 * author: emindemirci
 */
public class Consumption {

    private String applianceName;
    private int amount;
    private long timestamp;

    public Consumption(String applianceName, int amount, long timestamp) {
        this.applianceName = applianceName;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Consumption() {

    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Consumption that = (Consumption) o;

        if (amount != that.amount) {
            return false;
        }
        if (timestamp != that.timestamp) {
            return false;
        }
        return applianceName != null ? applianceName.equals(that.applianceName) : that.applianceName == null;
    }

    @Override
    public int hashCode() {
        int result = applianceName != null ? applianceName.hashCode() : 0;
        result = 31 * result + amount;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "applianceName='" + applianceName + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
