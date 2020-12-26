package com.vinnick.cryptotrade;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CurrencyHistoryAsync extends AsyncTask<String, Void, String> {

    private static final String API_LINK_BEGINNING = "https://api.nomics.com/v1/exchange-rates/history?key=";
    // private static final String API_LINK_END = "&currency=BTC&start=2018-04-14T00%3A00%3A00Z&end=2018-05-14T00%3A00%3A00Z";
    private static final String API_KEY = "66d6fc35ed618c1fbf359c96d88b671c";

    private CurrencyFragment currencyFragment;
    private DataPoint[] dataPoints;
    private GraphType type;

    public CurrencyHistoryAsync(CurrencyFragment fragment) {
        currencyFragment = fragment;
    }

    @Override
    protected void onPostExecute(String s) {
        if (dataPoints == null) {
            // error
            dataPoints = new DataPoint[] {
                    new DataPoint(0, 5),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 5),
                    new DataPoint(4, 5)
            };

        }

        //currencyFragment.updateGraph(dataPoints);
        currencyFragment.updateData(dataPoints, type);
    }

    @Override
    protected String doInBackground(String... strings) {
        // strings[0] - currency ("BTC","ETH","BSV","LTC")
        // strings[1] - type ("1D","1W","1M","3M","1Y","5Y")
        String currency = strings[0];
        String typeStr = strings[1];
        switch (typeStr) {
            case "1D":
                type = GraphType.DATA1D;
                break;
            case "1W":
                type = GraphType.DATA1W;
                break;
            case "1M":
                type = GraphType.DATA1M;
                break;
            case "3M":
                type = GraphType.DATA3M;
                break;
            case "1Y":
                type = GraphType.DATA1Y;
                break;
            case "5Y":
                type = GraphType.DATA5Y;
                break;
        }


        String url;
        String date;
        Calendar cal = Calendar.getInstance();
        Date result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");;
        switch (type) {
            case DATA1D:
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case DATA1W:
                List<DataPoint> tempDataPoints = new ArrayList<>();
                DataPoint tempDataPoint;
                for (int i = 0; i < 7; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -6+i);
                    result = cal.getTime();
                    String startDate = sdf.format(result);

                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -6+i+1);
                    result = cal.getTime();
                    String endDate = sdf.format(result);
                    url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + startDate + "T00%3A00%3A00Z" + "&end=" + endDate + "T00%3A00%3A00Z";
                    try {
                        parseDataPoints(requestData(url));
                        for (int j = 0; j < dataPoints.length; j++){
                            double x = dataPoints[j].getX();
                            double y = dataPoints[j].getY();
                            tempDataPoint = new DataPoint(x,y);
                            tempDataPoints.add(tempDataPoint);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                /*
                int hoursInAWeek = 168;
                for (int i = 0; i < 24-(tempDataPoints.size() - hoursInAWeek); i++) {
                    tempDataPoints.remove(0);
                }*/
                dataPoints = new DataPoint[tempDataPoints.size()];
                for (int i = 0; i < tempDataPoints.size(); i++) {
                    double y = tempDataPoints.get(i).getY();
                    dataPoints[i] = new DataPoint(i, y);
                }
                break;

            case DATA1M:
                cal.add(Calendar.MONTH, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case DATA3M:
                cal.add(Calendar.MONTH, -3);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case DATA1Y:
                cal.add(Calendar.YEAR, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case DATA5Y:
                cal.add(Calendar.YEAR, -5);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&currency=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
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

    public void parseDataPoints(JSONArray json) {
        try {
            dataPoints = new DataPoint[json.length()];
            for (int i = 0; i < json.length(); i++) {
                JSONObject data = json.getJSONObject(i);
                dataPoints[i] = new DataPoint(i, data.getDouble("rate"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
