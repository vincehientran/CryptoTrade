package com.vinnick.cryptotrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vinnick.cryptotrade.ActivitiesAndFragments.WatchlistFragment;

public class WatchlistHolderAdapter extends RecyclerView.Adapter<WatchlistHolder> {

    private Watchlist watchlist;
    private WatchlistFragment watchlistFragment;

    public WatchlistHolderAdapter(Watchlist watchlist, WatchlistFragment sFrag) {
        this.watchlistFragment = sFrag;
        this.watchlist = watchlist;
    }

    @NonNull
    @Override
    public WatchlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watchlist_holder, parent, false);

        itemView.setOnClickListener(watchlistFragment);

        return new WatchlistHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistHolder holder, int position) {
        WatchlistItem item = watchlist.get(position);

        String name = item.getName();
        String symbol = item.getSymbol();

        holder.textViewName.setText(name);
        holder.textViewSymbol.setText(symbol);
    }

    @Override
    public int getItemCount() {
        return watchlist.size();
    }
}
