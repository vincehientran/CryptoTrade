package com.vinnick.cryptotrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinnick.cryptotrade.ActivitiesAndFragments.SearchFragment;

import java.util.List;

public class SearchHolderAdapter extends RecyclerView.Adapter<SearchHolder> {

    private List<CryptoName> cryptoNameList;
    private SearchFragment searchFragment;
    private Watchlist watchlist;

    public SearchHolderAdapter(List<CryptoName> cList, SearchFragment sFrag, Watchlist wList) {
        this.cryptoNameList = cList;
        this.searchFragment = sFrag;
        this.watchlist = wList;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_holder, parent, false);

        itemView.setOnClickListener(searchFragment);

        return new SearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        CryptoName crypto = cryptoNameList.get(position);

        String name = crypto.getName();
        String symbol = crypto.getSymbol();

        holder.textViewName.setText(name);
        holder.textViewSymbol.setText(symbol);
        if (watchlist.contains(symbol)) {
            holder.buttonWatchlist.setText("Remove");
        }
        else {
            holder.buttonWatchlist.setText("Add");
        }
        holder.buttonWatchlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (holder.buttonWatchlist.getText().equals("Add")) {
                    holder.buttonWatchlist.setText("Remove");
                    watchlist.add(new WatchlistItem(name, symbol));
                }
                else {
                    holder.buttonWatchlist.setText("Add");
                    watchlist.removeSymbol(symbol);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cryptoNameList.size();
    }
}
