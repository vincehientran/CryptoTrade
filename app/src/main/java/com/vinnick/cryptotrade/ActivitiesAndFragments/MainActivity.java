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
import com.vinnick.cryptotrade.R;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private Button button;
    private TextView text;

    private BottomNavigationView.OnNavigationItemSelectedListener navBarListener;

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
        navBar.setOnNavigationItemSelectedListener(navBarListener);

        // set the first default fragment
        Fragment fragment = new PortfolioFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();

        text = findViewById(R.id.textView3);
        button = findViewById(R.id.button);

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

}