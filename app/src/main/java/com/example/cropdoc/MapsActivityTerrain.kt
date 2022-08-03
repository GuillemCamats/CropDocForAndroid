package com.example.cropdoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson

class MapsActivityTerrain :  AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private lateinit var mMap: GoogleMap
    private var mapView: MapView?= null
    private var marker: Marker?= null
    private var resetMarker: Button?= null
    private var saveMarkers: Button?= null
    private var markers: MutableList<Marker> ?= mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_terrain)
        mapView = findViewById(R.id.mapTerrain)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
        resetMarker = findViewById(R.id.resetMarkers)
        saveMarkers = findViewById(R.id.saveterrain)
        resetMarker?.setOnClickListener{
            eraseAll()
        }
        saveMarkers?.setOnClickListener{
            Log.d("markers",markers.toString())
            if(markers!=null){
                val terrains = Terrains("test",markers,null)
                Terrains.terrainsList.add(terrains)
                //val json = Gson().toJson(Terrains.terrainsList)
                //SharedApp.prefs.name = json// passa la dada de objecte a dins de pointlist despres de tancar la app
                finish()
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.setOnMapClickListener(this)
    }
    override fun onMapClick(p0: LatLng) {
        marker = mMap.addMarker(MarkerOptions().position(p0))
        marker?.let { markers?.add(it) ?: marker }
        Log.d("marker",marker.toString())
        Log.d("markers",markers.toString())
    }

    private fun eraseAll(){
        mMap.clear()
        marker?.remove()
        marker= null
        markers?.clear()
    }
}