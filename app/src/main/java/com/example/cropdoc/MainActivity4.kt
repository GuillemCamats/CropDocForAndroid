package com.example.cropdoc


import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

class MainActivity4 : AppCompatActivity() {//no s'utilitze
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val lonitude = findViewById<EditText>(R.id.longitude)
        val latitude = findViewById<EditText>(R.id.latitude)
        val button = findViewById<Button>(R.id.button2)
        val prediction = intent.getStringExtra("pred")
        val bitmap = intent.getStringExtra("bitmap")

        button.setOnClickListener{
            val lon = lonitude.text.toString()
            val lat = latitude.text.toString()
            val latlong = LatLng(lat.toDouble(),lon.toDouble())
            //val locations = Locations(latlong,prediction)

            //Locations.pointsList.add(locations)
            val json = Gson().toJson(Locations.pointsList)
            SharedApp.prefs.name = json// passa la dada de objecte a dins de pointlist despres de tancar la app
            Log.d("shape", SharedApp.prefs.name.toString())
            finish()
        }
    }
}
