package com.example.kesbokar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ProfileMarket extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LoaderManager.LoaderCallbacks<ArrayList<MarketProfileList>> busLoader;
    private static final int LOADER_BUS_PRO_LIST = 66;
    ListView listView;
    private  int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_market);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        id=extras.getInt("id");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.listProfileMarket);
        busLoader = new LoaderManager.LoaderCallbacks<ArrayList<MarketProfileList>>() {
            @Override
            public Loader<ArrayList<MarketProfileList>> onCreateLoader(int i, Bundle bundle) {
                LoaderBusProfileListMarket loaderBusProfileListMarket = new LoaderBusProfileListMarket(ProfileMarket.this,"http://serv.kesbokar.com.au/jil.0.1/v1/product?user_id=" + id);
                return loaderBusProfileListMarket;
            }

            @Override
            public void onLoadFinished(Loader<ArrayList<MarketProfileList>> loader, ArrayList<MarketProfileList> marketProfileLists) {
                if(marketProfileLists!=null) {
                    if (marketProfileLists.size() != 0) {
                        AdapterBusListProfileMarket adapterBusListProfileMarket = new AdapterBusListProfileMarket(ProfileMarket.this, marketProfileLists);
                        listView.setAdapter(adapterBusListProfileMarket);
                    } else {
                        Toast.makeText(ProfileMarket.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onLoaderReset(Loader<ArrayList<MarketProfileList>> loader) {

            }
        };
        getLoaderManager().initLoader(LOADER_BUS_PRO_LIST,null,busLoader);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.dashboard) {

        } else if (id == R.id.profile) {

        } else if (id == R.id.business_lg_page) {

        } else if (id == R.id.market_lg_page) {

        } else if (id == R.id.business_in) {

        } else if (id == R.id.market_in) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
