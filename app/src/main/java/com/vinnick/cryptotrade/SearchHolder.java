package com.vinnick.cryptotrade;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SearchHolder extends RecyclerView.ViewHolder{

    public TextView textViewName;
    public TextView textViewSymbol;
    public Button buttonWatchlist;

    public SearchHolder(View v) {
        super(v);
        textViewName = v.findViewById(R.id.textView_cryptoholder_name);
        textViewSymbol = v.findViewById(R.id.textView_cryptoholder_symbol);
        buttonWatchlist = v.findViewById(R.id.button_cryptoholder_watchlist);
    }
}
