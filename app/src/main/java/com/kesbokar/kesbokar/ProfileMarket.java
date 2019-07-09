package com.kesbokar.kesbokar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ProfileMarket extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LoaderManager.LoaderCallbacks<ArrayList<MarketProfileList>> busLoader;
    private static final int LOADER_BUS_PRO_LIST = 66;
    ListView listView;
    String loginId, loginPass, full_name, email, image, phone_no,created,updated;
    int id,flag;
    Button btnProductManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_market);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getData();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.listProfileMarket);
        btnProductManagement =(Button) findViewById(R.id.btnProductManagement);

        btnProductManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProductManagement = new Intent(ProfileMarket.this,ProductManagementActivity.class);
                startActivity(intentProductManagement);
            }
        });


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
                        AdapterBusListProfileMarket adapterBusListProfileMarket = new AdapterBusListProfileMarket(ProfileMarket.this, marketProfileLists,ProfileMarket.this);
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Intent intent = new Intent(ProfileMarket.this,LoginData.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0);
        overridePendingTransition(0, 0);
        finish();;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.dashboard) {
            Intent intent = new Intent(ProfileMarket.this, LoginData.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.profile) {
            Intent intent = new Intent(ProfileMarket.this, Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.business_lg_page) {
            Intent intent = new Intent(ProfileMarket.this, ProfileBusinessListing.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.manage_help_desk) {
            Intent intent = new Intent(ProfileMarket.this, ManageHelpDeskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.business_in) {
            Intent intent = new Intent(ProfileMarket.this, inbox_business.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();

        } else if (id == R.id.market_in) {
            Intent intent = new Intent(ProfileMarket.this, inbox_market.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getData()
    {
        SharedPreferences loginData=getSharedPreferences("data",0);
        flag = loginData.getInt("Flag",0);
        full_name=loginData.getString("Name","");
        email=loginData.getString("mail","");
        image=loginData.getString("image","");
        phone_no=loginData.getString("phone","");
        id=loginData.getInt("id",0);
        created=loginData.getString("create","");
        updated=loginData.getString("update","");

    }

}
