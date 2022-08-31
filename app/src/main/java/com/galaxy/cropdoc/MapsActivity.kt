package com.galaxy.cropdoc

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cropdoc.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private var mapView: MapView ?= null
    private var marker: Marker ?= null
    private var addMarker: Button ?= null
    private val REQUEST_LOCATION_PERMISSION = 1
    lateinit var loc: ArrayList<Locations>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapView = findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
        addMarker = findViewById(R.id.addMarker)
        addMarker?.setOnClickListener{
            if(marker!=null){
                val prediction = intent.getStringExtra("pred")
                Log.d("pred maps",prediction.toString())
                val foto = intent.getStringExtra("bitmap")
                val index = intent.getStringExtra("pos")
                val locations = Locations(marker!!.position,prediction,foto)
                if (index != null) {
                    val terrain = Terrains.terrainsList.get(index.toInt())
                    val tName = terrain.name
                    val tTerrain = terrain.terrain

                    if (terrain.trees==null){
                        loc = arrayListOf()
                        loc.add(locations)
                    }else{
                        loc = terrain.trees as ArrayList<Locations>
                        loc.add(locations)
                    }
                    Terrains.terrainsList.set(index.toInt(), Terrains(tName,tTerrain,loc))
                }
                Log.d("shape", Terrains.terrainsList.toString())
                val gson = Gson()
                val json = gson.toJson(Terrains.terrainsList)
                SharedApp.prefs.name = json// passa la dada de objecte a dins de pointlist despres de tancar la app
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.setOnMapClickListener(this)
        enableMyLocation()
    }
    override fun onMapClick(p0: LatLng) {
        val prediction = intent.getStringExtra("pred")?.split("|")
        eraseLastchekedPos()
        marker = mMap.addMarker(MarkerOptions().position(p0).title(prediction?.get(0)).snippet(
            prediction.toString()
        ))
        marker?.isVisible
        marker?.showInfoWindow()
        Log.d("marker",marker.toString())
    }
    private fun eraseLastchekedPos(){
        marker?.remove()
        marker = null
    }
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

}