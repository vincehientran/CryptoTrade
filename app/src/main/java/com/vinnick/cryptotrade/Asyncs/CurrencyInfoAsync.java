package com.vinnick.cryptotrade.Asyncs;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;
import com.vinnick.cryptotrade.ActivitiesAndFragments.CurrencyFragment;

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
    private static final String API_KEY = "66d6fc35ed618c1fbf359c96d88b671c";

    private CurrencyFragment currencyFragment;

    private String currency;
    private String url;

    private String name;
    private int circulatingSupply;
    private int maxSupply;
    private int marketCap;

    public CurrencyInfoAsync(CurrencyFragment fragment) {
        currencyFragment = fragment;
    }

    @Override
    protected void onPostExecute(String s) {
        currencyFragment.updateInfo(name, circulatingSupply, maxSupply, marketCap);
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
            circulatingSupply = obj.getInt("circulating_supply");
            maxSupply = obj.getInt("max_supply");
            marketCap = obj.getInt("market_cap");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
