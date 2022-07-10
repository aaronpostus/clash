package us.aaronpost.clash.Islands.Buildings.Collection;

import java.util.Currency;

public class Collector extends Thread {

    private final double collectionRatePerSecond;
    private final double capacity;
    private double currentTotal;
    private Currency currency;


    public Collector(double collectionRatePerHour, double currentTotal, double capacity) {
        this.collectionRatePerSecond = collectionRatePerHour / 60;
        this.capacity = capacity;
        this.currentTotal = currentTotal;
    }

    @Override
    public void run() {
        while(currentTotal < capacity) {
            try {
                currentTotal += collectionRatePerSecond;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentTotal = capacity;
    }

    public double getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(double currentTotal) {
        this.currentTotal = currentTotal;
    }
}
