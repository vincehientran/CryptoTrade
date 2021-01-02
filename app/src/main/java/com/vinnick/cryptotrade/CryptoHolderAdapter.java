package com.vinnick.cryptotrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinnick.cryptotrade.ActivitiesAndFragments.SearchFragment;

import java.util.List;

public class CryptoHolderAdapter extends RecyclerView.Adapter<CryptoHolder> {

    private List<CryptoName> cryptoNameList;
    private SearchFragment searchFragment;

    public CryptoHolderAdapter(List<CryptoName> cList, SearchFragment sFrag) {
        this.cryptoNameList = cList;
        this.searchFragment = sFrag;
    }

    @NonNull
    @Override
    public CryptoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_holder, parent, false);

        itemView.setOnClickListener(searchFragment);

        return new CryptoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoHolder holder, int position) {
        CryptoName crypto = cryptoNameList.get(position);

        holder.textViewName.setText(crypto.getName());
        holder.textViewSymbol.setText(crypto.getSymbol());
    }

    @Override
    public int getItemCount() {
        return cryptoNameList.size();
    }
}
