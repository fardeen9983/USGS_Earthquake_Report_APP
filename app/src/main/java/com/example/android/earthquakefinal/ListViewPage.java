package com.example.android.earthquakefinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by LUCIFER on 02-02-2018.
 */

public class ListViewPage extends AppCompatActivity {
    private Button done;
    private EditText from_date;
    private EditText to_date;
    private EditText limit;
    private StringBuilder stringBuilder;
    private String url=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.params_page);
        stringBuilder= new StringBuilder();
        url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-05-30&endtime=2016-05-01";//&orderby=time&minmag=3&limit=100";
        stringBuilder.append(url);
        done = findViewById(R.id.button);
        from_date = findViewById(R.id.from_date);
        to_date = findViewById(R.id.to_date);
        limit = findViewById(R.id.limit);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("url","https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-07-02");
                startActivity(intent);
            }
        });

    }
}
