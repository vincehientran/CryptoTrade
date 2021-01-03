package com.vinnick.cryptotrade;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;


public class WatchlistHolder extends RecyclerView.ViewHolder{

    public TextView textViewName;
    public TextView textViewSymbol;
    public TextView textViewDelta;

    public GraphView graphView;

    public WatchlistHolder(View v) {
        super(v);
        textViewName = v.findViewById(R.id.textView_watchlistholder_name);
        textViewSymbol = v.findViewById(R.id.textView_watchlistholder_symbol);
        textViewDelta = v.findViewById(R.id.textView_watchlistholder_delta);

        graphView = v.findViewById(R.id.graph_watchlistholder);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        
    }
}
