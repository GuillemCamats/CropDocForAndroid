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
    lateinit var markers: MutableList<Marker>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_terrain)
        mapView = findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
        resetMarker = findViewById(R.id.resetMarkers)
        saveMarkers = findViewById(R.id.saveterrain)
        resetMarker?.setOnClickListener{
            eraseAll()
        }
        saveMarkers?.setOnClickListener{
            if(markers.isNotEmpty()){
                val prediction = intent.getStringExtra("pred")
                val locations = Locations(marker!!.position,prediction)
                Locations.pointsList.add(locations)
                val json = Gson().toJson(Locations.pointsList)
                SharedApp.prefs.name = json// passa la dada de objecte a dins de pointlist despres de tancar la app
                Log.d("shape", SharedApp.prefs.name.toString())
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
        val prediction = intent.getStringExtra("pred")?.split("|")
        mMap.addMarker(MarkerOptions().position(p0).title(prediction?.get(0)).snippet(
            prediction.toString()
        ))?.let { markers.add(it) }
        marker?.isVisible
        marker?.showInfoWindow()
    }
    private fun eraseAll(){
        markers.clear()
    }
}