package com.vinnick.cryptotrade.Asyncs;

import android.os.AsyncTask;

import com.vinnick.cryptotrade.ActivitiesAndFragments.SearchFragment;
import com.vinnick.cryptotrade.BuildConfig;
import com.vinnick.cryptotrade.CryptoName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CryptoNameAsync extends AsyncTask<String, Void, String> {

    private static final String API_URL = "https://api.pro.coinbase.com/currencies";
    private static final String API_KEY = BuildConfig.COINBASE_KEY;
    private static final String API_PASSPHRASE = BuildConfig.COINBASE_PASSPHRASE;

    private SearchFragment searchFragment;

    private List<CryptoName> nameList;

    public CryptoNameAsync(SearchFragment fragment) {
        searchFragment = fragment;
    }

    @Override
    protected void onPostExecute(String s) {
        searchFragment.updateCryptoNames(nameList);
    }

    @Override
    protected String doInBackground(String... strings) {
        nameList = new ArrayList<>();
        try {
            getJSON();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void getJSON() throws IOException, JSONException {
        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("CB-ACCESS-KEY", API_KEY);
        con.setRequestProperty("CB-ACCESS-PASSPHRASE", API_PASSPHRASE);
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String jsonStr = content.toString();
        JSONArray json = new JSONArray(jsonStr);
        getNames(json);
    }

    private void getNames(JSONArray json) throws JSONException {
        for (int i = 0; i < json.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            String name = obj.getString("name");
            String symbol = obj.getString("id");
            if (symbol.equals("CGLD") || symbol.equals("BAT") ||
                    symbol.equals("LOOM") || symbol.equals("CVC") ||
                    symbol.equals("DNT") || symbol.equals("MANA") ||
                    symbol.equals("GNT") || symbol.equals("EUR") ||
                    symbol.equals("GBP") || symbol.equals("USD") ||
                    symbol.equals("USDC") || symbol.equals("WBTC") ||
                    symbol.equals("GRT")){
                // GRT too new
                continue;
            }
            else {
                if (symbol.equals("ETH")) {
                    name = "Ethereum";
                }
                else if (symbol.equals("ETC")) {
                    name = "Ethereum Classic";
                }
                nameList.add(new CryptoName(name, symbol));
            }
        }
    }
}
