package com.example.cropdoc;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Terrains implements Serializable {
    public final List<LatLng> terrain;
    public List<Locations> trees;
    public final String name;

    public static List<Terrains> terrainsList = new ArrayList<>();
    public static List<Terrains> demosList = new ArrayList<>();

    public Terrains(String name, List<LatLng> terrain, List<Locations> trees){
        this.terrain=terrain;
        this.trees=trees;
        this.name=name;
    }
    public static HashMap<String, Integer> getTerrainsListNames(){
        HashMap<String, Integer> names = new HashMap<>();
        int i = 0;
        for(Terrains terrain: terrainsList){
            names.put(terrain.name,i);
            i+=1;
        }
        return names;
    }
}
