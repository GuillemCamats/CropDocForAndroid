package com.galaxy.cropdoc;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Locations implements Serializable {
    public final LatLng coordinates;
    public final String prediction;
    public final String foto;

    public static List<Locations> pointsList = new ArrayList<>();


    public Locations (LatLng coordinates, String prediction, String foto){
        this.coordinates = coordinates;
        this.prediction = prediction;
        this.foto=foto;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getPrediction() {
        return prediction;
    }

}
