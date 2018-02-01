package com.example.android.earthquakefinal;

import java.util.Date;

/**
 * Created by LUCIFER on 01-02-2018.
 */

public class Earthquake {
    private String location;
    private Date date;
    private String url;
    private double magnitude;

    public Earthquake(String location) {
        this.location = location;
    }

    public Earthquake(double magnitude, String location, String url, Date date) {
        this.location = location;
        this.date = date;
        this.url = url;
        this.magnitude = magnitude;
    }

    public Earthquake() {
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public double getMagnitude() {
        return magnitude;
    }
}
