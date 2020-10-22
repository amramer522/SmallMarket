package com.amoor.SmallMarket.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amoor.SmallMarket.R;
import com.amoor.SmallMarket.data.model.Product;
import com.amoor.SmallMarket.helper.DBConnection;
import com.amoor.SmallMarket.helper.HelperMethods;
import com.amoor.SmallMarket.ui.fragment.AddProductFragment;
import com.amoor.SmallMarket.ui.fragment.HomeFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private TextView mTextMessage;
    private SearchView searchView;
    private HomeFragment homeFragment = new HomeFragment();
    private AddProductFragment addProductFragment = new AddProductFragment();
    private DBConnection db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Main2_Container, "HomeFragment");
        navigation.setSelectedItemId(R.id.navigation_home);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        db = new DBConnection(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cycle, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                ArrayList<Product> search_result = db.getSearch(query);
                homeFragment.setSearchResult(search_result);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String new_text) {
                homeFragment.setSearchResult(new_text);
                return true;
            }
        });

        return true;
    }




    @Override
    public void onBackPressed()
    {
        HomeFragment homeFragment1 = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
        AddProductFragment addProductFragment1 = (AddProductFragment) getSupportFragmentManager().findFragmentByTag("addProductFragment");
        if (homeFragment1 != null && homeFragment1.isVisible())
        {
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
            } else {
                finish();
            }

        } else if (addProductFragment1 != null && addProductFragment1.isVisible())
        {
            HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Main2_Container, "HomeFragment");
            searchView.setVisibility(View.VISIBLE);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Main2_Container, "HomeFragment");
                    searchView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    if (!searchView.isIconified())
                    {
                        searchView.setIconified(true);
                    }
                    HelperMethods.replace(addProductFragment, getSupportFragmentManager(), R.id.Main2_Container, "addProductFragment");
                    searchView.setVisibility(View.INVISIBLE);
                    return true;
//                case R.id.navigation_notifications:
//                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.search_with_camera) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You Cancelled the scanning", Toast.LENGTH_SHORT).show();
            } else {
                HomeFragment homeFragment1 = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
                AddProductFragment addProductFragment1 = (AddProductFragment) getSupportFragmentManager().findFragmentByTag("addProductFragment");

                if (homeFragment1 != null && homeFragment1.isVisible()) {
                    ArrayList<Product> search_result = db.getSearch(result.getContents());
                    homeFragment.setSearchResult(search_result);

                } else if (addProductFragment1 != null && addProductFragment1.isVisible()) {
                    addProductFragment.setProductIDFromCamera(result.getContents());

                }

                ArrayList<Product> search_result = db.getSearch(result.getContents());
                this.homeFragment.setSearchResult(search_result);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
