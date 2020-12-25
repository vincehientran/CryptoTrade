package com.vinnick.cryptotrade;

import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrencyHistoryAsync extends AsyncTask<String, Void, String> {

    private static final String API_LINK_BEGINNING = "https://api.nomics.com/v1/exchange-rates/history?key=";
    // private static final String API_LINK_END = "&currency=BTC&start=2018-04-14T00%3A00%3A00Z&end=2018-05-14T00%3A00%3A00Z";
    private static final String API_KEY = "66d6fc35ed618c1fbf359c96d88b671c";



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
        // strings[0] - currency ("BTC","ETH","BSV","LTC")
        // strings[1] - type ("1D","1W","1M","3M","1Y","5Y")
        String currency = strings[0];
        String type = strings[1];


        String url;
        String date;
        Calendar cal = Calendar.getInstance();
        Date result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");;
        switch (type) {
            case "1D":
                cal.add(Calendar.DATE, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                break;

            case "1W":
                cal.add(Calendar.DATE, -7);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                break;

            case "1M":
                cal.add(Calendar.MONTH, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                break;

            case "3M":
                cal.add(Calendar.MONTH, -3);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                break;

            case "1Y":
                cal.add(Calendar.YEAR, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                break;

            case "5Y":
                cal.add(Calendar.YEAR, -5);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                break;
        }

        return null;
    }
}
