package com.vinnick.cryptotrade;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CryptoHolder extends RecyclerView.ViewHolder{

    public TextView textViewName;
    public TextView textViewSymbol;

    public CryptoHolder(View v) {
        super(v);
        textViewName = v.findViewById(R.id.textView_cryptoholder_name);
        textViewSymbol = v.findViewById(R.id.textView_cryptoholder_symbol);
    }
}
