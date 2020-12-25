package com.vinnick.cryptotrade;

import android.os.AsyncTask;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;
import com.vinnick.cryptotrade.ActivitiesAndFragments.CurrencyFragment;

public class CurrencyPriceAsync extends AsyncTask<String, Void, String> {

    private CurrencyFragment currencyFragment;
    private String textPrice;

    public CurrencyPriceAsync(CurrencyFragment fragment) {
        currencyFragment = fragment;
    }

    @Override
    protected void onPostExecute(String s) {
        currencyFragment.updatePrice(textPrice);
    }

    @Override
    protected String doInBackground(String... strings) {
        CurrentPrice cp = new CurrentPrice(strings[0]);

        textPrice = String.format("%.2f", cp.getPrice());
        return null;
    }
}
