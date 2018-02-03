package com.example.android.earthquakefinal;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquake>> {
    private ListView listView;
    private ArrayList<Earthquake> earthquakes;
    private EarthquakeAdapter earthquakeAdapter;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_main);

        url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&starttime=2014-01-01&endtime=2014-02-01&limit=50";
        listView = findViewById(R.id.list_item);
        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        getLoaderManager().initLoader(1, null, this);

        listView.setAdapter(earthquakeAdapter);
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {

        return new EarthquakeLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        if (data != null)
            updateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        updateUI(new ArrayList<Earthquake>());
    }


    public void updateUI(final ArrayList<Earthquake> earthquakes) {
        earthquakeAdapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            earthquakeAdapter.addAll(earthquakes);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = earthquakes.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl())));
            }
        });
    }
}
