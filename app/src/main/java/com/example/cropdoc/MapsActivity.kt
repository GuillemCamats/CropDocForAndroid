package com.example.cropdoc

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private var mapView: MapView ?= null
    private var marker: Marker ?= null
    private var addMarker: Button ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapView = findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
        addMarker = findViewById(R.id.addMarker)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.setOnMapClickListener(this)
    }

    override fun onMapClick(p0: LatLng) {
        Log.d("pos",p0.toString())
        val prediction = intent.getStringExtra("pred")
        marker = mMap.addMarker(MarkerOptions().position(p0).title("MarcadorExemple").snippet(prediction))
        marker?.isVisible
        marker?.showInfoWindow()
    }


}