package com.example.kesbokar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarketListing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private DataAdapterMarket dataAdapter;
    private ArrayList<MarketIem> marketIems;
    private RequestQueue requestQueue;

    private Button btnHelp,btnBuis,btnMar,btnTop;

    private ScrollView scrollView;

    private AutoCompleteTextView autoCompleteTextViewOne,autoCompleteTextViewTwo;
    private Button btnAlertDialogSearch;

    private static final int LOADER_ID_MARVAL = 3;
    private static final int LOADER_ID_MARSUB = 4;
    private static final int LOADER_ID_BTNSRCH = 5;

    private LoaderManager.LoaderCallbacks<ArrayList<String>> marketSearch;
    private LoaderManager.LoaderCallbacks<ArrayList<StateAndSuburb>> marketSub;
    private LoaderManager.LoaderCallbacks<ArrayList<MarketIem>> btnSearch;

    private ArrayList<String> valsMarket;
    private ArrayList<StateAndSuburb> valsSub;
    private ArrayList<MarketIem> marketItems;
    private String query = "";
    String querySub,subV,subType,q;
    int stateid = 0;

    boolean isLoading = false;
    String name,image,synopsis,url1,city,city_id;
    int id;
    String loginId, loginPass, full_name, email, image1, phone_no,created,updated,title;
    int id1,flag;
    double ratings;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buisness__listing);

        getData();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View ab = navigationView.getHeaderView(0);
        Menu show=navigationView.getMenu();
        TextView name1=(TextView)ab.findViewById(R.id.name_user);
        Button signup=(Button)ab.findViewById(R.id.signup);
        Button login=(Button)ab.findViewById(R.id.login);
        Button logout=ab.findViewById(R.id.logout);

        btnHelp = (Button)findViewById(R.id.help);
        btnBuis = (Button)findViewById(R.id.buis);
        btnMar = (Button)findViewById(R.id.mar);
        btnTop = (Button)findViewById(R.id.top);

        final ScrollView scrollView=(ScrollView)findViewById(R.id.scroll);

        intent=getIntent();
        bundle=intent.getExtras();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        marketIems = new ArrayList<>();
        marketItems = new ArrayList<>();
        valsSub = new ArrayList<>();
        querySub = subV = subType = q = "";

        ImageView imageView = (ImageView)findViewById(R.id.imgSearch);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestAlertDialogBox();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketListing.this, Login.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketListing.this, SignUp.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                SharedPreferences loginData= getSharedPreferences("data",0);
                SharedPreferences.Editor editor=loginData.edit();
                editor.putInt("Flag",flag);
                editor.apply();
                Intent intent=new Intent(MarketListing.this,Navigation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketListing.this,Help.class);
                intent.putExtra("Flag", flag);
                intent.putExtra("Name",full_name);
                intent.putExtra("mail",email);
                intent.putExtra("image",image);
                intent.putExtra("phone",phone_no);
                intent.putExtra("create",created);
                intent.putExtra("update",updated);
                intent.putExtra("id",id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btnBuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketListing.this,Navigation.class);
                intent.putExtra("Flag", flag);
                intent.putExtra("Name",full_name);
                intent.putExtra("mail",email);
                intent.putExtra("image",image);
                intent.putExtra("phone",phone_no);
                intent.putExtra("create",created);
                intent.putExtra("update",updated);
                intent.putExtra("id",id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        /*btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });*/


        if(flag==1)
        {
            name1.setText(full_name);
            login.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);
            show.findItem(R.id.nav_send).setVisible(true);
            show.findItem(R.id.nav_share).setVisible(true);
            show.findItem(R.id.advertise).setVisible(true);
            logout.setVisibility(View.VISIBLE);
            show.findItem(R.id.loginPage).setVisible(true);
            show.findItem(R.id.loginPage).setTitle(full_name+"  GO!!");
        }
        String denote = bundle.getString("CHOICE");
        if(denote.equals("imgBtnService")){
            imgBtnService();
        }else if(denote.equals("btnSearch")){
            marketIems = bundle.getParcelableArrayList("ARRAYLIST");
            dataAdapter = new DataAdapterMarket(MarketListing.this,marketIems);
            recyclerView.setAdapter(dataAdapter);
        }


    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MarketListing.this,Navigation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0);
        overridePendingTransition(0, 0);
        finish();
    }

    public void imgBtnService(){
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
        initScrollListener();
    }

    private void RequestAlertDialogBox()
    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // get the layout inflater
//        LayoutInflater inflater = this.getLayoutInflater();
//
//        // inflate and set the layout for the dialog
//        // pass null as the parent view because its going in the dialog layout
//        builder.setView(inflater.inflate(R.layout.search_alert_dialog_box, null))
//                .show();
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.search_alert_dialog_box);
        autoCompleteTextViewOne = dialog.findViewById(R.id.autoCompleteTextViewOne);
        autoCompleteTextViewTwo = dialog.findViewById(R.id.autoCompleteTextViewTwo);
        btnAlertDialogSearch = dialog.findViewById(R.id.btnAlertDialogSearch);


        btnSearch = new LoaderManager.LoaderCallbacks<ArrayList<MarketIem>>() {
            @Override
            public Loader<ArrayList<MarketIem>> onCreateLoader(int id, Bundle args) {
                LoaderBtnSrchMarket loaderBtnSearch = new LoaderBtnSrchMarket(MarketListing.this,q,subV,"http://serv.kesbokar.com.au/jil.0.1/v2/product",stateid,subType,0.0,0.0);
                return loaderBtnSearch;
            }

            @Override
            public void onLoadFinished(Loader<ArrayList<MarketIem>> loader, ArrayList<MarketIem> data) {
                switch (loader.getId()){
                    case LOADER_ID_BTNSRCH:
                        if(data != null && q.length()!=0){
                            marketItems = data;
                            Log.i("Search", data.toString());
                            Intent intent = new Intent(MarketListing.this,MarketListing.class);
                            intent.putExtra("CHOICE", "btnSearch");
                            intent.putParcelableArrayListExtra("ARRAYLIST",marketItems);
                            startActivity(intent);
                            //Toast.makeText(Navigation_market.this, data, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

            @Override
            public void onLoaderReset(Loader<ArrayList<MarketIem>> loader) {

            }
        };
        marketSub = new LoaderManager.LoaderCallbacks<ArrayList<StateAndSuburb>>() {
            @Override
            public Loader<ArrayList<StateAndSuburb>> onCreateLoader(int id, Bundle args) {
                LoaderBusSuburb loaderBusSuburb = new LoaderBusSuburb(MarketListing.this,querySub,"http://serv.kesbokar.com.au/jil.0.1/v2/product/search/cities");
                return loaderBusSuburb;
            }

            @Override
            public void onLoadFinished(Loader<ArrayList<StateAndSuburb>> loader, ArrayList<StateAndSuburb> data) {
                if (data.size() != 0) {
                    valsSub = data;
                    Log.i("Tag Sub", valsSub + "");
                    ArrayAdapter<StateAndSuburb> adapter=new ArrayAdapter<StateAndSuburb>(MarketListing.this,android.R.layout.simple_dropdown_item_1line,valsSub);

                    autoCompleteTextViewTwo.setAdapter(adapter);
                } else {
                    Toast.makeText(MarketListing.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }
                getLoaderManager().destroyLoader(LOADER_ID_MARVAL);
            }

            @Override
            public void onLoaderReset(Loader<ArrayList<StateAndSuburb>> loader) {

            }
        };
        marketSearch = new LoaderManager.LoaderCallbacks<ArrayList<String>>() {
            @Override
            public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args) {
                LoaderMarketSearch loaderMarketSearch= new LoaderMarketSearch(MarketListing.this,"","http://serv.kesbokar.com.au/jil.0.1/v2/product/search");
                return loaderMarketSearch;
            }

            @Override
            public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> data) {
                if (data.size() != 0) {
                    valsMarket = data;
                    Log.i("Tag", valsMarket + "");
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(MarketListing.this,android.R.layout.simple_dropdown_item_1line,valsMarket);

                    autoCompleteTextViewOne.setAdapter(adapter);
                    getLoaderManager().initLoader(LOADER_ID_MARSUB,null,marketSub);
                } else {
                    Toast.makeText(MarketListing.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLoaderReset(Loader<ArrayList<String>> loader) {

            }
        };

        getLoaderManager().initLoader(LOADER_ID_MARVAL, null, marketSearch);
        dialog.show();
        btnAlertDialogSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q = autoCompleteTextViewOne.getText().toString();
                Log.i("Q and subV", q + " " + subV);
                if(q.length() == 0 && subV.length() == 0){
                    Toast.makeText(MarketListing.this, "Cannot Search Empty fields", Toast.LENGTH_SHORT).show();
                }
                else if (subV.length()==0)
                {
                    Toast.makeText(MarketListing.this, "Cannot Search Empty State", Toast.LENGTH_SHORT).show();
                }
                getLoaderManager().initLoader(LOADER_ID_BTNSRCH,null,btnSearch);
            }
        });

        autoCompleteTextViewTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StateAndSuburb stateAndSuburb = (StateAndSuburb) parent.getAdapter().getItem(position);
                subV = stateAndSuburb.getValue();
                stateid = stateAndSuburb.getId();
                subType = stateAndSuburb.getType();
            }
        });
        autoCompleteTextViewTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                querySub = s.toString();
                getLoaderManager().restartLoader(LOADER_ID_MARSUB,null,marketSub);            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void parseJSON() {
        String url =bundle.getString("URL");
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dat = jsonArray.getJSONObject(i);
                                Log.i("JSON PAGI",dat.toString());
                                name = dat.getString("name");
                                synopsis = dat.getString("description");
                                image = dat.getString("image");
                                url1=dat.getString("url_name");
                                city_id=dat.getString("city_id");
                                title=dat.getString("cat_title");

                                if (city_id!="null") {
                                    JSONObject cityob=dat.getJSONObject("city");

                                    city = cityob.getString("title");
                                    city = city.replace(" ", "+");
                                }
                                else
                                {
                                    city="city";
                                }

                                id=dat.getInt("id");
                                marketIems.add(new MarketIem(image, name, synopsis,url1,city,id,title));
                            }
                            dataAdapter = new DataAdapterMarket(MarketListing.this, marketIems);
                            recyclerView.setAdapter(dataAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == marketIems.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        //exampleItems.add(null);
//        dataAdapter.notifyItemInserted(marketIems.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                marketIems.remove(marketIems.size() - 1);
                int scrollPosition = marketIems.size();
                dataAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    marketIems.add(new MarketIem(image, name, synopsis,url1,city,id,title));
                    currentSize++;
                }

                dataAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_send) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.about) {
            Intent about=new Intent(MarketListing.this,About.class);
            startActivity(about);

        } else if (id == R.id.career) {
            Intent career=new Intent(MarketListing.this,Career.class);
            startActivity(career);

        } else if (id == R.id.advertise) {

        }else if (id == R.id.loginPage) {
            Intent intent=new Intent(MarketListing.this,LoginData.class);
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
        image1=loginData.getString("image","");
        phone_no=loginData.getString("phone","");
        id1=loginData.getInt("id",0);
        created=loginData.getString("create","");
        updated=loginData.getString("update","");

    }
}
