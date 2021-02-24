package com.vinnick.cryptotrade.Asyncs;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;
import com.vinnick.cryptotrade.ActivitiesAndFragments.CurrencyFragment;
import com.vinnick.cryptotrade.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class CurrencyInfoAsync extends AsyncTask<String, Void, String> {

    private static final String API_LINK_BEGINNING = "https://api.nomics.com/v1/currencies/ticker?key=";
    //https://api.nomics.com/v1/currencies/ticker?key=demo-26240835858194712a4f8cc0dc635c7a&ids=BTC&interval=1d
    private static final String API_KEY = BuildConfig.NOMICS_KEY;

    private CurrencyFragment currencyFragment;

    private String currency;
    private String url;

    private String name;
    private String circulatingSupply;
    private String maxSupply;
    private String marketCap;

    public CurrencyInfoAsync(CurrencyFragment fragment) {
        currencyFragment = fragment;
    }

    @Override
    protected void onPostExecute(String s) {

        // this is for when requestData(url) gives an error
        if (name != null && circulatingSupply != null && maxSupply != null && marketCap != null) {
            currencyFragment.updateInfo(name, circulatingSupply, maxSupply, marketCap);
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        currency = strings[0];
        url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&interval=1d";

        try {
            parseInfo(requestData(url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONArray requestData(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public void parseInfo(JSONArray json) {
        try {
            JSONObject obj = json.getJSONObject(0);
            name = obj.getString("name");
        } catch (JSONException e) {
            name = "N/A";
        }

        try {
            JSONObject obj = json.getJSONObject(0);
            double cSupplyDouble = obj.getDouble("circulating_supply");
            long cSupply = (long) cSupplyDouble;
            String cSupplyStr = "";
            if (cSupply > 10_000_000_000L) {
                cSupplyStr = (cSupply / 1_000_000_000L) + "B";
            }
            else if (cSupply > 1_000_000L) {
                cSupplyStr = (cSupply / 1_000_000L) + "M";
            }
            else {
                cSupplyStr = cSupply + "";
            }
            circulatingSupply = cSupplyStr;
        } catch (JSONException e) {
            circulatingSupply = "N/A";
        }

        try {
            JSONObject obj = json.getJSONObject(0);
            double mSupplyDouble = obj.getDouble("max_supply");
            long mSupply = (long) mSupplyDouble;
            String mSupplyStr = "";
            if (mSupply > 10_000_000_000L) {
                mSupplyStr = (mSupply / 1_000_000_000L) + "B";
            }
            else if (mSupply > 1_000_000L) {
                mSupplyStr = (mSupply / 1_000_000L) + "M";
            }
            else {
                mSupplyStr = mSupply + "";
            }
            maxSupply = mSupplyStr;
        } catch (JSONException e) {
            maxSupply = "N/A";
        }

        try {
            JSONObject obj = json.getJSONObject(0);
            double mCapDouble = obj.getDouble("market_cap");
            long mCap = (long) mCapDouble;
            String mCapStr = "";
            if (mCap > 10_000_000_000L) {
                mCapStr = (mCap / 1_000_000_000L) + "B";
            }
            else if (mCap > 1_000_000L) {
                mCapStr = (mCap / 1_000_000L) + "M";
            }
            else {
                mCapStr = mCap + "";
            }
            marketCap = mCapStr;
        } catch (JSONException e) {
            marketCap = "N/A";
        }

    }
}
