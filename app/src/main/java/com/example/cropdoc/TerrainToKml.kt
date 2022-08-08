package com.example.cropdoc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class TerrainToKml : AppCompatActivity() {
    lateinit var setIpToConn: EditText
    lateinit var connect: Button
    lateinit var sendKml: Button
    lateinit var startOrbit: Button
    lateinit var stopOrbit: Button
    lateinit var listKml: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terrain_to_kml)
        setIpToConn = findViewById(R.id.lg_ip)
        connect = findViewById(R.id.connectKml)
        sendKml = findViewById(R.id.sendKml)
        startOrbit = findViewById(R.id.startOrbit)
        stopOrbit = findViewById(R.id.stopOrbit)
        listKml = findViewById(R.id.list_kmls)
        connect.setBackgroundColor(Color.RED)
    }

}