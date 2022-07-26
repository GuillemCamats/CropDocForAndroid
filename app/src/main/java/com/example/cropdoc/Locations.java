package com.example.cropdoc;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Locations implements Serializable {
    public final LatLng coordinates;
    public final String prediction;
    public static List<Locations> pointsList = new ArrayList<Locations>();


    public Locations (LatLng coordinates, String prediction){
        this.coordinates = coordinates;
        this.prediction = prediction;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getPrediction() {
        return prediction;
    }

}
