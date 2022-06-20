package com.example.cropdoc;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Locations {
    public final String coordinates;
    public final String prediction;
    public final String bitmap;
    public static List<Locations> pointsList = new ArrayList<Locations>();


    public Locations (String coordinates, String prediction, String bitmap){
        this.coordinates = coordinates;
        this.prediction = prediction;
        this.bitmap=bitmap;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getPrediction() {
        return prediction;
    }

    public String getBitmap() {
        return bitmap;
    }
}
