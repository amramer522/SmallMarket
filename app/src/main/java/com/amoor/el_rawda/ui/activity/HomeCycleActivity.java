package com.amoor.el_rawda.ui.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.amoor.el_rawda.R;
import com.amoor.el_rawda.data.model.Product;
import com.amoor.el_rawda.helper.DBConnection;
import com.amoor.el_rawda.helper.HelperMethods;
import com.amoor.el_rawda.ui.fragment.AddProductFragment;
import com.amoor.el_rawda.ui.fragment.HomeFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeCycleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private DBConnection db;
    private HomeFragment homeFragment = new HomeFragment();
    private AddProductFragment addProductFragment = new AddProductFragment();
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        db = new DBConnection(this);

        toolbar.setTitle(R.string.Products);
        HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Content_Home_Cycle_Fragment_Container, "HomeFragment");


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            finish();
        }
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
            public boolean onQueryTextSubmit(String query)
            {
//                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
//                ArrayList<Product> search_result = db.getSearch(query);
//                homeFragment.setSearchResult(search_result);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String new_text)
            {
                ArrayList<Product> search_result = db.getSearch(new_text);
                homeFragment.setSearchResult(search_result);

//                 Toast.makeText(getApplicationContext(), new_text, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }

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
//                    searchView.setVisibility(View.INVISIBLE);
                }
//
//                if (fragment_id.getId() == R.id.Add_Product_Fragment_Ll_Container)
//                {
////                    ArrayList<Product> search_result = db.getSearch(result.getContents());
//                    addProductFragment.setProductIDFromCamera(result.getContents());
////                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
//                }
//                else if (fragment_id.getId()==R.id.Home_Fragment_Ll_Container)
//                {
//                    ArrayList<Product> search_result = db.getSearch(result.getContents());
//                    homeFragment.setSearchResult(search_result);
//                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(this, fragment.getTag(), Toast.LENGTH_SHORT).show();

                ArrayList<Product> search_result = db.getSearch(result.getContents());
                this.homeFragment.setSearchResult(search_result);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
//        if (id == R.id.add_product) {
//            AddProductFragment addProductFragment = new AddProductFragment();
//            toolbar.setTitle(R.string.add_product);
//            HelperMethods.replace(addProductFragment, getSupportFragmentManager(), R.id.Content_Home_Cycle_Fragment_Container);
//
//            // Handle the camera action
//        }
        if (id == R.id.home) {
            HomeFragment homeFragment = new HomeFragment();
            toolbar.setTitle("Home");
            HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Content_Home_Cycle_Fragment_Container, "HomeFragment");
            fab.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
        }
//        else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @SuppressLint("RestrictedApi")
    @OnClick(R.id.fab)
    public void onViewClicked() {
        toolbar.setTitle(R.string.add_product);
        HelperMethods.replace(addProductFragment, getSupportFragmentManager(), R.id.Content_Home_Cycle_Fragment_Container, "addProductFragment");
        fab.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);
    }
}
