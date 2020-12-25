package com.vinnick.cryptotrade;

import android.os.AsyncTask;

public class CurrencyHistoryAsync extends AsyncTask<String, Void, String> {

    private CurrencyFragment currencyFragment;

    CurrencyHistoryAsync(CurrencyFragment fragment) {
        currencyFragment = fragment;
    }

    @Override
    protected void onPostExecute(String s) {
        //currencyFragment.updateData(wData, bitmap);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
