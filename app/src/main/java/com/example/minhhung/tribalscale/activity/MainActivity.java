package com.example.minhhung.tribalscale.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhhung.tribalscale.R;
import com.example.minhhung.tribalscale.adapter.ResultAdapter;
import com.example.minhhung.tribalscale.api.ApiService;
import com.example.minhhung.tribalscale.api.Client;
import com.example.minhhung.tribalscale.model.Location;
import com.example.minhhung.tribalscale.model.Result;
import com.example.minhhung.tribalscale.model.ResultsList;
import com.example.minhhung.tribalscale.util.InternetConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * Views
     */
    private ListView listView;
    private View parentView;

    private ArrayList<Result> resultList;
    private ResultAdapter adapter;
    private static int _counter = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Array List for Binding Data from JSON to this List
         */
        resultList = new ArrayList<Result>();

        parentView = findViewById(R.id.parentLayout);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location location = resultList.get(position).getLocation();
                if(location!=null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(location.getStreet()+"\n");
                    sb.append(location.getCity()+" "+location.getState()+" "+location.getPostcode().toString()+"\n");
                    sb.append(resultList.get(position).getPhone());
                    Snackbar.make(parentView,  sb.toString(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        loadJSON();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View view) {

                loadJSON();
            }
        });
    }

    private void loadJSON(){
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.string_getting_json_title));
            dialog.setMessage(getString(R.string.string_getting_json_message));
            dialog.show();
            //Creating an object of our api interface
            ApiService api = new Client(this).getApiService();
            Map<String, String> params = new HashMap<String, String>();
            _counter++;
            params.put("page", String.valueOf(_counter));
            params.put("results", "10");
            params.put("seed", "abc");

            Call<ResultsList> call = api.getRandomJSON(params);

            call.enqueue(new Callback<ResultsList>() {
                @Override
                public void onResponse(Call<ResultsList> call, Response<ResultsList> response) {
                    dialog.dismiss();

                    if(response.isSuccessful()) {
                        ResultsList rs = response.body();
                        resultList = rs.getResults();
                        /**
                         * Binding that List to Adapter
                         */
                        adapter = new ResultAdapter(MainActivity.this, resultList);
                        listView.setAdapter(adapter);

                    } else {
                        Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultsList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
        }
    }
}
