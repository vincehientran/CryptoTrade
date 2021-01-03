package com.vinnick.cryptotrade;

import com.jjoe64.graphview.series.DataPoint;

public class WatchlistItem {

    private String name;
    private String symbol;
    private double delta;
    private DataPoint[] dataPoints;

    public WatchlistItem(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public DataPoint[] getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(DataPoint[] dataPoints) {
        this.dataPoints = dataPoints;
    }
}
