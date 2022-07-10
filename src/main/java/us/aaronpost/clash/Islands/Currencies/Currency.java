package us.aaronpost.clash.Islands.Currencies;

import java.io.Serializable;

public class Currency implements Serializable {
    private int currency = 0;
    private int maxCurrency = 0;
    private String inGameName = "";
    public Currency() {

    }

    public Currency(int initialCurrency, int maxCurrency) {
        currency = initialCurrency;
        this.maxCurrency = maxCurrency;
    }
    public int getMaxCurrency() {
        return maxCurrency;
    }
    public void setMaxCurrency(int maxCurrency) {
        this.maxCurrency = maxCurrency;
    }
    public int getAmount() {
        return currency;
    }
    public void setIngameName(String inGameName) {
        this.inGameName = inGameName;
    }
    public String getInGameName() {
        return inGameName;
    }

    public void setAmount(int amount) {
        currency=amount;
    }

    public boolean isMaxed() {
        return currency == maxCurrency;
    }

    public boolean canAfford(int amount) {
        return (currency - amount) >= 0;
    }

    public int increase(int amount) {
        if((currency + amount) > maxCurrency) {
            currency = maxCurrency;
        } else {
            currency += amount;
        }
        return currency;
    }

    public int decrease(int amount) {
        if((currency - amount) >= 0) {
            currency -= amount;
        } else {
            currency = 0;
        }
        return currency;
    }
}
