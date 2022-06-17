package com.example.cropdoc


import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val location = findViewById<EditText>(R.id.longitude)
        val button = findViewById<Button>(R.id.button2)
        val prediction = intent.getStringExtra("pred")
        val bitmap = intent.getStringExtra("bitmap")

        button.setOnClickListener{
            val text = location.text.toString()
            val locations = Locations(text,prediction,bitmap)
            Locations.pointsList.add(locations)
            Log.d("shape", Locations.pointsList.toString())
            finish()
        }
    }

}
