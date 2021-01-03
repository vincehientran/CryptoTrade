package com.vinnick.cryptotrade.ActivitiesAndFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.vinnick.cryptotrade.Asyncs.CryptoNameAsync;
import com.vinnick.cryptotrade.SearchHolderAdapter;
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
    private EditText editTextSearch;
    private View view;
    private RecyclerView recyclerView;

    private SearchHolderAdapter adapter;
    private List<CryptoName> cryptoNameList = new ArrayList<>();
    private List<CryptoName> cryptoNameListPermanentStored = new ArrayList<>();

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

        editTextSearch = view.findViewById(R.id.editText_search_search);

        new CryptoNameAsync(this).execute();

        recyclerView = view.findViewById(R.id.recyclerView_search);
        adapter = new SearchHolderAdapter(cryptoNameList, this, ((MainActivity)getActivity()).getWatchlist());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search_search:
                cryptoNameList.clear();
                cryptoNameList.addAll(cryptoNameListPermanentStored);
                // hides the keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String text = editTextSearch.getText().toString();
                if (text.equals("")) {
                    cryptoNameList.clear();
                    cryptoNameList.addAll(cryptoNameListPermanentStored);
                }
                else {
                    List<CryptoName> temp = new ArrayList<>();
                    for (CryptoName c : cryptoNameList) {
                        if (c.match(text)) {
                            temp.add(new CryptoName(c.getName(), c.getSymbol()));
                        }
                    }
                    cryptoNameList.clear();
                    cryptoNameList.addAll(temp);
                }
                adapter.notifyDataSetChanged();
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
        cryptoNameList.clear();
        cryptoNameList.addAll(nList);
        cryptoNameListPermanentStored.clear();
        cryptoNameListPermanentStored.addAll(nList);
        adapter.notifyDataSetChanged();
    }

}