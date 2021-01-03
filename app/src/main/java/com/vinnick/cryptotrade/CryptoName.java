package com.vinnick.cryptotrade;

public class CryptoName {

    private String name;
    private String symbol;

    public CryptoName(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean match(String text) {
        if (name.toLowerCase().contains(text.toLowerCase()) || symbol.toLowerCase().contains(text.toLowerCase())) {
            return true;
        }
        else {
            return false;
        }
    }
}
