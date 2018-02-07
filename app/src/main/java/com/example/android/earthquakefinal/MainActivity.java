package com.example.android.earthquakefinal;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquake>> {
    private ListView listView;
    private EarthquakeAdapter earthquakeAdapter;
    private String url;
    private TextView emptyView;
    private final String TAG = getClass().getSimpleName();
    private ProgressBar progressBar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&starttime=2014-01-01&endtime=2014-02-01&limit=50";
        listView = findViewById(R.id.list_item);
        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        if(checkConnectivity()){
            getLoaderManager().initLoader(1, null, this);
            listView.setAdapter(earthquakeAdapter);
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No internet connection");
        }
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.v(TAG, "onCreateLoader() called");
        return new EarthquakeLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        progressBar.setVisibility(View.GONE);
        Log.v(TAG, "onLoadFinished() called");
        if (data != null)
            updateUI(data);
        emptyView.setText("No Earthquake Data Found");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.v(TAG, "onLoaderFinished() called");
        updateUI(new ArrayList<Earthquake>());
    }

    public void updateUI(final ArrayList<Earthquake> earthquakes) {
        Log.v(TAG, "updateUI() called");
        earthquakeAdapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            earthquakeAdapter.addAll(earthquakes);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = new Earthquake();
                try {
                    earthquake = earthquakes.get(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl())));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settings)
            startActivity(new Intent(getApplicationContext(),Settings.class));
        return super.onOptionsItemSelected(item);
    }

    private boolean checkConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null&&networkInfo.isConnectedOrConnecting());

    }
}