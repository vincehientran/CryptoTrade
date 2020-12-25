package com.vinnick.cryptotrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

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

    private GraphView graph;
    private Button button1D;
    private Button button1W;
    private Button button1M;
    private Button button3M;

    private LineGraphSeries<DataPoint> graphSeries;

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
        View view = inflater.inflate(R.layout.fragment_currency, container, false);

        graph = (GraphView) view.findViewById(R.id.graph_currency);
        graphSeries = new LineGraphSeries<>(generateSeries1D());
        graphSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        graph.addSeries(graphSeries);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        button1D = view.findViewById(R.id.button_currency_1d);
        button1D.setOnClickListener(this);
        button1W = view.findViewById(R.id.button_currency_1w);
        button1W.setOnClickListener(this);
        button1M = view.findViewById(R.id.button_currency_1m);
        button1M.setOnClickListener(this);
        button3M = view.findViewById(R.id.button_currency_3m);
        button3M.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_currency_1d:
                graphSeries.resetData(generateSeries1D());
                break;
            case R.id.button_currency_1w:
                graphSeries.resetData(generateSeries1W());
                break;
            case R.id.button_currency_1m:
                graphSeries.resetData(generateSeries1M());
                break;
            case R.id.button_currency_3m:
                graphSeries.resetData(generateSeries3M());
                break;
        }
    }

    public DataPoint[] generateSeries1D(){
        int count = 120;
        DataPoint[] data = new DataPoint[count];
        for (int i=0; i < count; i++) {
            double x = i;
            double y = Math.sin(Math.PI * i*0.05);
            DataPoint v = new DataPoint(x, y);
            data[i] = v;
        }
        return data;
    }

    public DataPoint[] generateSeries1W(){
        int count = 120;
        DataPoint[] data = new DataPoint[count];
        for (int i=0; i < count; i++) {
            double x = i;
            double y = Math.sin(Math.PI * i*0.1);
            DataPoint v = new DataPoint(x, y);
            data[i] = v;
        }
        return data;
    }

    public DataPoint[] generateSeries1M(){
        int count = 120;
        DataPoint[] data = new DataPoint[count];
        for (int i=0; i < count; i++) {
            double x = i;
            double y = Math.sin(Math.PI * i*0.3);
            DataPoint v = new DataPoint(x, y);
            data[i] = v;
        }
        return data;
    }

    public DataPoint[] generateSeries3M(){
        int count = 120;
        DataPoint[] data = new DataPoint[count];
        for (int i=0; i < count; i++) {
            double x = i;
            double y = Math.sin(Math.PI * i*0.6);
            DataPoint v = new DataPoint(x, y);
            data[i] = v;
        }
        return data;
    }
}