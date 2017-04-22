package com.android.rahul.helloworld;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prahul on 4/15/2017.
 */

public class ImageLocationData {
    private LatLng coordinates;
    private String city;
    private String zipCode;

    public ImageLocationData(LatLng coor) {
        this.coordinates = coor;
        // TODO: Need a way to find city and zipcode from coordinates
        this.city = "ActualCity";
        this.zipCode = "00000";

        // Reference - https://developers.google.com/maps/documentation/geocoding/intro#ReverseGeocoding
        // https://maps.googleapis.com/maps/api/geocode/json?
        // latlng=40.714224,-73.961452&key=<key>
        // locality, administrative_area_level_1 (state name), postal_code
    }

    public ImageLocationData(/* String imgId, */ LatLng coor, String city, String code) {
        this.coordinates = coor;
        // TODO: Need a way to find city and zipcode from coordinates
        this.city = city;
        this.zipCode = code;
    }

    public ImageLocationData getImageLocationData() {
        return this;
    }

    public LatLng getLatLng() {
        return this.coordinates;
    }

    public String getCity() {
        return this.city;
    }
}
