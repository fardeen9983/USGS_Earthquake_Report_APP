package com.example.android.earthquakefinal;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LUCIFER on 01-02-2018.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        int colorId;
        String[] loc = new String[2];
        if (root == null)
            root = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_view, parent, false);
        TextView magnitude = root.findViewById(R.id.magnitude);
        TextView location1 = root.findViewById(R.id.loc1);
        TextView location2 = root.findViewById(R.id.loc2);
        TextView time = root.findViewById(R.id.time);

        Earthquake earthquake = getItem(position);

        if (earthquake.getLocation().contains("of ")) {
            loc = earthquake.getLocation().split("of ");
        }
        else
        {
            loc[0]="Near";
            loc[1]=earthquake.getLocation();
        }
        int mag =(int)Math.floor(earthquake.getMagnitude());
        switch (mag) {
            case 0:
            case 1:
                colorId = R.color.magnitude1;
                break;
            case 2:
                colorId = R.color.magnitude2;
                break;
            case 3:
                colorId = R.color.magnitude3;
                break;
            case 4:
                colorId = R.color.magnitude4;
                break;
            case 5:
                colorId = R.color.magnitude5;
                break;
            case 6:
                colorId = R.color.magnitude6;
                break;
            case 7:
                colorId = R.color.magnitude7;
                break;
            case 8:
                colorId = R.color.magnitude8;
                break;
            case 9:
                colorId = R.color.magnitude9;
                break;
            default:
                colorId =R.color.magnitude10plus;
                break;
        }
        int colorResourceId = ContextCompat.getColor(getContext(),colorId);
        GradientDrawable gradientDrawable = (GradientDrawable) magnitude.getBackground();
        gradientDrawable.setColor(colorResourceId);
        magnitude.setText(earthquake.getMagnitude() + "");
        location1.setText(loc[0]);
        location2.setText(loc[1]);
        time.setText(convertDateToString(earthquake.getDate()));

        return root;
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy - h:mm a");
        return simpleDateFormat.format(date);
    }
}
