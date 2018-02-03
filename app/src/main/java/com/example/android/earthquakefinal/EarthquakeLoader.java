package com.example.android.earthquakefinal;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUCIFER on 04-02-2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        ArrayList<Earthquake> earthquakes;
        if (!url.isEmpty())
            earthquakes = FetchResult.fetchData(url);
        else
            return null;
        return earthquakes;
    }

}
