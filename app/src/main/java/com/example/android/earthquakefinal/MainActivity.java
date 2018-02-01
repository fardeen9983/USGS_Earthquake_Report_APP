package com.example.android.earthquakefinal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EarthquakeTask earthquakeTask;
    private ListView listView;
    private EarthquakeAdapter earthquakeAdapter;
    private final String url1 =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=100",
            url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=100";
    private Button done;
    private EditText from_date;
    private EditText to_date;
    private EditText limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.params_page);
        listView = findViewById(R.id.list_item);
        done = findViewById(R.id.button);
        from_date = findViewById(R.id.from_date);
        to_date= findViewById(R.id.to_date);
        limit =findViewById(R.id.limit);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earthquakeTask = new EarthquakeTask();
                earthquakeTask.execute(url);
            }
        });

    }

    private class EarthquakeTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null)
                return null;
            return FetchResult.fetchData(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            updateUI(earthquakes);
        }
    }

    public void updateUI(final ArrayList<Earthquake> earthquakes) {
        earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);
        listView.setAdapter(earthquakeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = earthquakes.get(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl())));
            }
        });
    }
}
