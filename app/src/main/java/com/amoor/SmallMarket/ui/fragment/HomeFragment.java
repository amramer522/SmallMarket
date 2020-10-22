package com.amoor.SmallMarket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amoor.SmallMarket.R;
import com.amoor.SmallMarket.adapter.ProductsAdapter;
import com.amoor.SmallMarket.data.model.Product;
import com.amoor.SmallMarket.helper.DBConnection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.Home_Fragment_Rv_Recycler)
    RecyclerView HomeFragmentRvRecycler;
    Unbinder unbinder;
    private DBConnection db;
    private ProductsAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);

        db = new DBConnection(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        HomeFragmentRvRecycler.setLayoutManager(linearLayoutManager);
        adapter = new ProductsAdapter(getActivity());
        ArrayList<Product> all_products = db.getAllRecords();
        adapter.setData(all_products);
        HomeFragmentRvRecycler.setAdapter(adapter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setSearchResult(ArrayList<Product> search_result) {
        adapter.setData(search_result);
        adapter.notifyDataSetChanged();
    }

    public void setSearchResult(String new_text) {
        ArrayList<Product> search_result = db.getSearch(new_text);
        adapter.setData(search_result);
        adapter.notifyDataSetChanged();
    }


}

