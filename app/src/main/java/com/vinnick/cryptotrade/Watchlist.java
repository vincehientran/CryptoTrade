package com.vinnick.cryptotrade;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {

    private List<WatchlistItem> watchlist;

    public Watchlist() {
        this.watchlist = new ArrayList<>();
    }

    public WatchlistItem get(int idx) {
        return watchlist.get(idx);
    }

    public int size(){
        return watchlist.size();
    }

    public void add(WatchlistItem item) {
        watchlist.add(item);
    }

    public void removeIndex(int idx) {
        watchlist.remove(idx);
    }

    public void removeSymbol(String s) {
        for (int i = 0; i < watchlist.size(); i++) {
            if (watchlist.get(i).getSymbol().equals(s)) {
                watchlist.remove(i);
                return;
            }
        }
    }

    public boolean contains(String s) {
        for (int i = 0; i < watchlist.size(); i++) {
            if (watchlist.get(i).getSymbol().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
