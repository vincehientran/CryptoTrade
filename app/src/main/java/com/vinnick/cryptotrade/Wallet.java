package com.vinnick.cryptotrade;

import java.util.HashMap;

public class Wallet {

    private HashMap <String, Double> wallet;

    public Wallet() {
        this.wallet = new HashMap<>();
        addCurrency("USD", 0);
    }

    public HashMap <String, Double> getWallet() {
        return wallet;
    }

    public void addCurrency (String currency, double amt) {
        if (!wallet.containsKey(currency)){
            wallet.put(currency, amt);
        } else {
            double currAmt = getCurrency(currency);
            wallet.put(currency, currAmt + amt);
        }
    }

    public void subCurrency (String currency, double amt) {
        try {
            double currAmt = getCurrency(currency);
            wallet.put(currency, currAmt - amt);
        }
        catch (Exception ex) {
            System.out.printf("%S not in wallet\n", currency);
        }
    }

    public double getTotalUSD () {
        double total = getCurrency("USD");
        for (String i : wallet.keySet()){
            if (i != "USD"){
                double p = new CurrentPrice(i).getPrice();
                total = total + (getCurrency(i) * p);
            }
        }
        return total;
    }

    public double getUSDOf (String currency) {
        if (!wallet.containsKey(currency)){
            return -1;
        } else {
            double price = new CurrentPrice(currency).getPrice();
            return (getCurrency(currency) * price);
        }
    }

    public boolean buyAmtInCrypto (String currency, double crypAmt) {
        double costInUSD = new CurrentPrice(currency).getPrice() * crypAmt;
        if (costInUSD > getCurrency("USD")){
            return false;
        } else {
            addCurrency(currency, crypAmt);
            subCurrency("USD", costInUSD);
            return true;
        }
    }

    public boolean buyAmtInUSD (String currency, double USDAmt) {
        double costInCryp = USDAmt / new CurrentPrice(currency).getPrice();
        if (USDAmt > getCurrency("USD")){
            return false;
        } else {
            addCurrency(currency, costInCryp);
            subCurrency("USD", USDAmt);
            return true;
        }
    }

    public boolean sellAmtInCrypto (String currency, double crypAmt) {
        double soldInUSD = new CurrentPrice(currency).getPrice() * crypAmt;
        if (getCurrency(currency) < crypAmt || !wallet.containsKey(currency)){
            return false;
        } else {
            subCurrency(currency, crypAmt);
            addCurrency("USD", soldInUSD);
            if (getCurrency(currency) == 0) wallet.remove(currency);
            return true;
        }
    }

    public boolean sellAmtInUSD (String currency, double USDAmt) {
        double soldInCryp = USDAmt / new CurrentPrice(currency).getPrice();
        if (getCurrency(currency) < soldInCryp || !wallet.containsKey(currency)){
            return false;
        } else {
            subCurrency(currency, soldInCryp);
            addCurrency("USD", USDAmt);
            if (getCurrency(currency) == 0) wallet.remove(currency);
            return true;
        }
    }

    public boolean sellAll(String currency) {
        if (!wallet.containsKey(currency)){
            return false;
        } else {
            double soldAmt = new CurrentPrice(currency).getPrice() * getCurrency(currency);
            addCurrency("USD", soldAmt);
            wallet.remove(currency);
            return true;
        }
    }

    public double getCurrency (String currency) {
        if (!wallet.containsKey(currency)){
            return -1;
        } else {
            return wallet.get(currency);
        }
    }
}