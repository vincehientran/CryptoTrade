package com.vinnick.cryptotrade.Asyncs;

import android.os.AsyncTask;

import com.jjoe64.graphview.series.DataPoint;
import com.vinnick.cryptotrade.ActivitiesAndFragments.CurrencyFragment;
import com.vinnick.cryptotrade.GraphType;

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
import java.util.TimeZone;

public class CurrencyHistoryAsync extends AsyncTask<String, Void, String> {

    private static final String API_LINK_BEGINNING = "https://api.nomics.com/v1/currencies/sparkline?key=";
    private static final String API_KEY = "66d6fc35ed618c1fbf359c96d88b671c";

    private CurrencyFragment currencyFragment;
    private DataPoint[] dataPoints;
    private List<String[]> datetimeValue;
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
        currencyFragment.updateData(dataPoints, type, datetimeValue);
    }

    @Override
    protected String doInBackground(String... strings) {
        // strings[0] - currency ("BTC","ETH","BSV","LTC")
        // strings[1] - type ("1D","1W","1M","3M","1Y","5Y")
        datetimeValue = new ArrayList<>();
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
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        switch (type) {
            case DATA1D:
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case DATA1W:
                List<DataPoint> tempDataPoints1W = new ArrayList<>();
                DataPoint tempDataPoint1W;
                for (int i = 0; i < 8; i++) {
                    cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    cal.add(Calendar.DATE, -7+i);
                    result = cal.getTime();
                    String startDate = sdf.format(result);

                    cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    cal.add(Calendar.DATE, -7+i+1);
                    result = cal.getTime();
                    String endDate = sdf.format(result);
                    url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&start=" + startDate + "T00%3A00%3A00Z" + "&end=" + endDate + "T00%3A00%3A00Z";
                    try {
                        parseDataPoints(requestData(url));
                        for (int j = 0; j < dataPoints.length; j++){
                            double x = dataPoints[j].getX();
                            double y = dataPoints[j].getY();
                            tempDataPoint1W = new DataPoint(x,y);
                            tempDataPoints1W.add(tempDataPoint1W);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                int toDelete1W = tempDataPoints1W.size() - 168;
                toDelete1W = 0;
                for (int i = 0; i < toDelete1W; i++) {
                    tempDataPoints1W.remove(0);
                    datetimeValue.remove(0);
                }
                dataPoints = new DataPoint[tempDataPoints1W.size()];
                for (int i = 0; i < tempDataPoints1W.size(); i++) {
                    double y = tempDataPoints1W.get(i).getY();
                    dataPoints[i] = new DataPoint(i, y);
                }
                break;

            case DATA1M:
                cal.add(Calendar.MONTH, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&start=" + date + "T00%3A00%3A00Z";
                try {
                    parseDataPoints(requestData(url));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case DATA3M:
                List<DataPoint> tempDataPoints3M = new ArrayList<>();
                DataPoint tempDataPoint3M;
                for (int i = 0; i < 3; i++) {
                    cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    cal.add(Calendar.MONTH, -3+i);
                    result = cal.getTime();
                    String startDate = sdf.format(result);

                    cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    cal.add(Calendar.MONTH, -3+i+1);
                    result = cal.getTime();
                    String endDate = sdf.format(result);
                    url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&start=" + startDate + "T00%3A00%3A00Z" + "&end=" + endDate + "T00%3A00%3A00Z";
                    try {
                        parseDataPoints(requestData(url));
                        for (int j = 0; j < dataPoints.length; j++){
                            double x = dataPoints[j].getX();
                            double y = dataPoints[j].getY();
                            tempDataPoint3M = new DataPoint(x,y);
                            tempDataPoints3M.add(tempDataPoint3M);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                int toDelete3M = tempDataPoints3M.size() - 168;
                toDelete3M = 0;
                for (int i = 0; i < toDelete3M; i++) {
                    tempDataPoints3M.remove(0);
                    datetimeValue.remove(0);
                }
                dataPoints = new DataPoint[tempDataPoints3M.size()];
                for (int i = 0; i < tempDataPoints3M.size(); i++) {
                    double y = tempDataPoints3M.get(i).getY();
                    dataPoints[i] = new DataPoint(i, y);
                }
                break;

            case DATA1Y:
                cal.add(Calendar.YEAR, -1);
                result = cal.getTime();
                date = sdf.format(result);
                url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&start=" + date + "T00%3A00%3A00Z";
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
                url = API_LINK_BEGINNING + API_KEY + "&ids=" + currency + "&start=" + date + "T00%3A00%3A00Z";
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
            JSONArray arrayPrices = json.getJSONObject(0).getJSONArray("prices");
            JSONArray arrayTimestamp = json.getJSONObject(0).getJSONArray("timestamps");
            dataPoints = new DataPoint[arrayPrices.length()];
            for (int i = 0; i < arrayPrices.length(); i++) {
                dataPoints[i] = new DataPoint(i, arrayPrices.getDouble(i));
                String[] datetimeValuePair = new String[2];
                String datetimeString = arrayTimestamp.getString(i);
                datetimeValuePair[0] = datetimeString.substring(0,10) + " " +
                        datetimeString.substring(11,13) +  ":" + datetimeString.substring(14,16) + "UTC";
                datetimeValuePair[1] = arrayPrices.getString(i);
                datetimeValue.add(datetimeValuePair);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
