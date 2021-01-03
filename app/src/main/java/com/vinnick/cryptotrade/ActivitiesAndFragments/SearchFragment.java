package com.vinnick.cryptotrade.ActivitiesAndFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vinnick.cryptotrade.Asyncs.CryptoNameAsync;
import com.vinnick.cryptotrade.CryptoHolderAdapter;
import com.vinnick.cryptotrade.CryptoName;
import com.vinnick.cryptotrade.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button buttonSearch;
    private View view;
    private RecyclerView recyclerView;

    private CryptoHolderAdapter adapter;
    private List<CryptoName> cryptoNameList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        buttonSearch = (Button) view.findViewById(R.id.button_search_search);
        buttonSearch.setOnClickListener(this);

        new CryptoNameAsync(this).execute();

        recyclerView = view.findViewById(R.id.recyclerView_search);
        adapter = new CryptoHolderAdapter(cryptoNameList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search_search:

                break;
            default:
                int pos = recyclerView.getChildLayoutPosition(v);
                CryptoName cryptoName = cryptoNameList.get(pos);
                goToCurrencyFragment(cryptoName.getSymbol());
                break;

        }
    }

    public void goToCurrencyFragment(String currency){
        Bundle bundle = new Bundle();
        bundle.putString("currency", currency);
        Fragment fragment = new CurrencyFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    public void updateCryptoNames(List<CryptoName> nList) {
        for (int i = 0; i < nList.size(); i++) {
            cryptoNameList.add(nList.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}