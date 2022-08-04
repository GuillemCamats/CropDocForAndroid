package com.example.cropdoc;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
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
