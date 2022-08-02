package com.example.cropdoc;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class Terrains {
    public List<Marker> terrain;
    public List<Locations> trees;
    public String name;

    public static List<Terrains> terrainsList = new ArrayList<>();

    public Terrains(String name, List<Marker> terrain, List<Locations> trees){
        this.terrain=terrain;
        this.trees=trees;
        this.name=name;
    }
    public static List<String> getTerrainsListNames(){
        List<String> names = new ArrayList<>();
        for(Terrains terrain: terrainsList){
            names.add(terrain.name);
        }
        return names;
    }
}
