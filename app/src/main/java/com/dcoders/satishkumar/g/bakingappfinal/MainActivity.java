package com.dcoders.satishkumar.g.bakingappfinal;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Receipe;
import com.dcoders.satishkumar.g.bakingappfinal.adapterClasses.MainAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String SAVEINSTANCEKEY="SAVEINSTANCE";
    public static final String url="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    List<Receipe> receipeslist;
    Receipe[] receipes;
    @BindView(R.id.main_recyclerView)
    RecyclerView main_recyclerView;
    @BindView(R.id.main_progressBar)
    ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        ButterKnife.bind(this);
        //
        receipeslist=new ArrayList<>();
        //
        if(savedInstanceState!=null)
        {
            if(savedInstanceState.getSerializable(SAVEINSTANCEKEY)!=null){
                receipeslist = (List<Receipe>) savedInstanceState.getSerializable(SAVEINSTANCEKEY);
            }
            else if(isNetworkAvailable())
            {
                getData();
            }
            else{
                showAlert();
            }
        }
        else if(isNetworkAvailable())
        {
            getData();
        }
        else {
            showAlert();
        }
    }
    private void showAlert()
    {
        new AlertDialog.Builder(this)
                .setTitle("INTERNET IS NOT AVAILABLE")
                .setMessage("Connect to the Internet. Click 'YES' to close app!")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void getData() {
        mainProgressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mainProgressBar.setVisibility(View.GONE);
                Gson gson = new GsonBuilder().create();
                receipes = gson.fromJson(response, Receipe[].class);
                receipeslist.addAll(Arrays.asList(receipes));
                main_recyclerView.setAdapter(new MainAdapter(MainActivity.this, receipeslist));
                main_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(request);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(receipeslist!=null)
        {
            outState.putSerializable(SAVEINSTANCEKEY, (Serializable) receipeslist);
        }
    }
}

