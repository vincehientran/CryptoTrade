package com.vinnick.cryptotrade.ActivitiesAndFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vinnick.cryptotrade.BuildConfig;
import com.vinnick.cryptotrade.R;
import com.vinnick.cryptotrade.Watchlist;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener navBarListener;

    private String user;

    private Watchlist watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set bottom navigation bar
        BottomNavigationView navBar = findViewById(R.id.bottomNavigationView);
        navBarListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.portfolioFragment:
                        goToPortfolioFragment();
                        break;

                    case R.id.watchlistFragment:
                        goToWatchlistFragment();
                        break;

                    case R.id.searchFragment:
                        goToSearchFragment();
                        break;
                }
                return true;
            }
        };

        user = getIntent().getStringExtra("user");

        watchlist = new Watchlist();

        navBar.setOnNavigationItemSelectedListener(navBarListener);

        // set the first default fragment
        Fragment fragment = new PortfolioFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    // methods to switch between fragments
    public void goToPortfolioFragment(){
        Fragment fragment = new PortfolioFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }
    public void goToWatchlistFragment(){
        Fragment fragment = new WatchlistFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }
    public void goToSearchFragment(){
        Fragment fragment = new SearchFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchList(Watchlist watchList) {
        this.watchlist = watchList;
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public String getUserEmail() {
        return user;
    }
}