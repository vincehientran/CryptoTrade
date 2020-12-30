package com.vinnick.cryptotrade.ActivitiesAndFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.vinnick.cryptotrade.Asyncs.CurrencyHistoryAsync;
import com.vinnick.cryptotrade.Asyncs.CurrencyInfoAsync;
import com.vinnick.cryptotrade.GraphType;
import com.vinnick.cryptotrade.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private GraphView graph;
    private Button button1D;
    private Button button1W;
    private Button button1M;
    private Button button3M;
    private Button button1Y;
    private Button button5Y;
    private TextView textViewPrice;
    private TextView textViewCurrency;
    private TextView textViewDatetime;
    private TextView textViewValue;
    private TextView textViewMarketCap;
    private TextView textViewMaxSupply;
    private TextView textViewCirculatingSupply;

    private String currency;
    private LineGraphSeries<DataPoint> graphSeries;
    private DataPoint[] dataPoints1D;
    private DataPoint[] dataPoints1W;
    private DataPoint[] dataPoints1M;
    private DataPoint[] dataPoints3M;
    private DataPoint[] dataPoints1Y;
    private DataPoint[] dataPoints5Y;
    private GraphType type;
    private OkHttpClient client;
    private List<String[]> datetimeValues1D;
    private List<String[]> datetimeValues1W;
    private List<String[]> datetimeValues1M;
    private List<String[]> datetimeValues3M;
    private List<String[]> datetimeValues1Y;
    private List<String[]> datetimeValues5Y;
    private List<String[]> datetimeValues;
    private WebSocket ws;

    public CurrencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencyFragment newInstance(String param1, String param2) {
        CurrencyFragment fragment = new CurrencyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_currency, container, false);
        textViewPrice = view.findViewById(R.id.textView_currency_price);
        textViewCurrency = view.findViewById(R.id.textView_currency_title);
        textViewDatetime = view.findViewById(R.id.textView_currency_datetime);
        textViewValue = view.findViewById(R.id.textView_currency_value);
        textViewMarketCap = view.findViewById(R.id.textView_currency_market_cap);
        textViewMaxSupply = view.findViewById(R.id.textView_currency_max_supply);
        textViewCirculatingSupply = view.findViewById(R.id.textView_currency_circulating_supply);

        currency = "DOGE";

        graph = (GraphView) view.findViewById(R.id.graph_currency);
        graphSeries = new LineGraphSeries<>(new DataPoint[0]);
        graphSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                int idx = (int) dataPoint.getX();
                String datetimeString = datetimeValues.get(idx)[0];
                String valueString = datetimeValues.get(idx)[1];
                textViewDatetime.setText(datetimeString);
                textViewValue.setText(valueString);
            }
        });
        graph.addSeries(graphSeries);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        button1D = view.findViewById(R.id.button_currency_1d);
        button1D.setOnClickListener(this);
        button1W = view.findViewById(R.id.button_currency_1w);
        button1W.setOnClickListener(this);
        button1M = view.findViewById(R.id.button_currency_1m);
        button1M.setOnClickListener(this);
        button3M = view.findViewById(R.id.button_currency_3m);
        button3M.setOnClickListener(this);
        button1Y = view.findViewById(R.id.button_currency_1y);
        button1Y.setOnClickListener(this);
        button5Y = view.findViewById(R.id.button_currency_5y);
        button5Y.setOnClickListener(this);

        dataPoints1D = new DataPoint[0];
        dataPoints1W = new DataPoint[0];
        dataPoints1M = new DataPoint[0];
        dataPoints3M = new DataPoint[0];
        dataPoints1Y = new DataPoint[0];
        dataPoints5Y = new DataPoint[0];
        type = GraphType.DATA1D;

        loadData();

        client = new OkHttpClient();


        new Thread() {
            @Override
            public void run(){
                startWS();
            }
        }.start();

        new CurrencyInfoAsync(this).execute(currency);


        return view;
    }

    @Override
    public void onPause() {
        ws.send("{\n" +
                "    \"type\": \"unsubscribe\",\n" +
                "    \"channels\": [\"ticker\"]\n" +
                "}");

        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_currency_1d:
                type = GraphType.DATA1D;
                new CurrencyHistoryAsync(this).execute(currency, "1D");
                break;
            case R.id.button_currency_1w:
                type = GraphType.DATA1W;
                break;
            case R.id.button_currency_1m:
                type = GraphType.DATA1M;
                break;
            case R.id.button_currency_3m:
                type = GraphType.DATA3M;
                break;
            case R.id.button_currency_1y:
                type = GraphType.DATA1Y;
                break;
            case R.id.button_currency_5y:
                type = GraphType.DATA5Y;
                break;
        }
        updateGraph();
    }

    private void loadData() {
        new CurrencyHistoryAsync(this).execute(currency, "1D");
        new CurrencyHistoryAsync(this).execute(currency, "1W");
        new CurrencyHistoryAsync(this).execute(currency, "1M");
        new CurrencyHistoryAsync(this).execute(currency, "3M");
        new CurrencyHistoryAsync(this).execute(currency, "1Y");
        new CurrencyHistoryAsync(this).execute(currency, "5Y");
    }

    public void updateGraph() {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        switch (type) {
            case DATA1D:
                graphSeries.resetData(dataPoints1D);
                datetimeValues = datetimeValues1D;
                break;
            case DATA1W:
                graphSeries.resetData(dataPoints1W);
                datetimeValues = datetimeValues1W;
                break;
            case DATA1M:
                graphSeries.resetData(dataPoints1M);
                datetimeValues = datetimeValues1M;
                break;
            case DATA3M:
                graphSeries.resetData(dataPoints3M);
                datetimeValues = datetimeValues3M;
                break;
            case DATA1Y:
                graphSeries.resetData(dataPoints1Y);
                datetimeValues = datetimeValues1Y;
                break;
            case DATA5Y:
                graphSeries.resetData(dataPoints5Y);
                datetimeValues = datetimeValues5Y;
                break;
        }


        double maxX = graph.getViewport().getMaxX(true);
        double maxY = graph.getViewport().getMaxY(true);
        double minX = graph.getViewport().getMinX(true);
        double minY = graph.getViewport().getMinY(true);

        graph.getViewport().setMinX(minX);
        graph.getViewport().setMinY(minY);
        if (type == GraphType.DATA1D) {
            graph.getViewport().setMaxX(24);
        }
        else {
            graph.getViewport().setMaxX(maxX);
        }
        graph.getViewport().setMaxY(maxY);

    }

    public void updateData(DataPoint[] data, GraphType dataType, List<String[]> datetimeValues) {
        switch (dataType) {
            case DATA1D:
                dataPoints1D = data;
                datetimeValues1D = datetimeValues;
                break;
            case DATA1W:
                dataPoints1W = data;
                datetimeValues1W = datetimeValues;
                break;
            case DATA1M:
                dataPoints1M = data;
                datetimeValues1M = datetimeValues;
                break;
            case DATA3M:
                dataPoints3M = data;
                datetimeValues3M = datetimeValues;
                break;
            case DATA1Y:
                dataPoints1Y = data;
                datetimeValues1Y = datetimeValues;
                break;
            case DATA5Y:
                dataPoints5Y = data;
                datetimeValues5Y = datetimeValues;
                break;
        }

        updateGraph();
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"product_ids\": [\n" +
                    "        \"" + currency + "-USD\"\n" +
                    "    ],\n" +
                    "    \"channels\": [\n" +
                    "        \"ticker\"\n" +
                    "    ]\n" +
                    "}");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            try {
                JSONObject json = new JSONObject(text);
                String type = json.getString("type");
                if (type.equals("ticker")) {
                    String priceStr = json.getString("price");
                    if (priceStr.contains(".")) {
                        if (priceStr.indexOf('.') == priceStr.length() - 2){
                            updateTextViewPrice(priceStr + "0");
                        }
                        else {
                            updateTextViewPrice(priceStr);
                        }
                    }
                    else {
                        updateTextViewPrice(priceStr + ".00");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            updateTextViewPrice("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            updateTextViewPrice("Error : " + t.getMessage());
        }
    }


    private void startWS() {
        Request request = new Request.Builder().url("wss://ws-feed.pro.coinbase.com").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();
    }

    private void updateTextViewPrice(final String txt) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPrice.setText("$" + txt);
            }
        });
    }

    public void updateInfo(String name, String circulatingSupply, String maxSupply, String marketCap) {
        textViewMarketCap.setText(marketCap);
        textViewCirculatingSupply.setText(circulatingSupply);
        textViewMaxSupply.setText(maxSupply);
        textViewCurrency.setText(name);
    }

}