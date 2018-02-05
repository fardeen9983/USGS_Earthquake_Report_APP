package com.example.android.earthquakefinal;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUCIFER on 04-02-2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String url;
    private final String TAG = getClass().getSimpleName();
    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(TAG,"onStartLoading() called - forceLoad()");
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.v(TAG,"loadInBackground() called");
        ArrayList<Earthquake> earthquakes;
        if (!url.isEmpty())
            earthquakes = FetchResult.fetchData(url);
        else
            return null;
        return earthquakes;
    }

}
