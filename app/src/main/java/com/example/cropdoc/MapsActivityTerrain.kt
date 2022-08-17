package com.example.cropdoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
    lateinit var nameT: EditText
    private var markers: MutableList<LatLng> ?= mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_terrain)
        mapView = findViewById(R.id.mapTerrain)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
        resetMarker = findViewById(R.id.resetMarkers)
        saveMarkers = findViewById(R.id.saveterrain)
        nameT = findViewById(R.id.editTextTerrain)
        resetMarker?.setOnClickListener{
            eraseAll()
        }
        saveMarkers?.setOnClickListener{

            if(markers!=null && !nameT.text.toString().equals(null)){
                val terrains = Terrains(nameT.text.toString(),markers,null)
                Terrains.terrainsList.add(terrains)
                Log.d("terr",Terrains.terrainsList.toString())
                val intent = Intent(this,SelectTerrainActivity::class.java)
                startActivity(intent)
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
        marker?.let { markers?.add(it.position) }
    }

    private fun eraseAll(){
        mMap.clear()
        marker?.remove()
        marker= null
        markers?.clear()
    }
}