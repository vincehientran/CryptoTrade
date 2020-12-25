package com.vinnick.cryptotrade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class CurrentPrice {

    private static final String API_LINK_BEGINNING = "https://api.nomics.com/v1/currencies/ticker?key=";
    //https://api.nomics.com/v1/currencies/ticker?key=demo-26240835858194712a4f8cc0dc635c7a&ids=BTC&interval=1d
    private static final String API_KEY = "66d6fc35ed618c1fbf359c96d88b671c";

    private String currency;
    private double price;

    public CurrentPrice(String currency) {
        this.currency = currency;
        this.price = 0;
    }

    public double getPrice() {
        String url = getUrl();
        try {
            JSONArray array = requestData(url);
            JSONObject object = array.getJSONObject(0);
            price = object.getDouble("price");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return price;
    }

    public String getUrl() {
        String url = API_LINK_BEGINNING + API_KEY;
        url += "&ids=" + currency;
        url += "&interval=" + "1d";
        return url;
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

}
