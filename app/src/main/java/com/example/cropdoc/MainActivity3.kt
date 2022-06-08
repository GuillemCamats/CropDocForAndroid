package com.example.cropdoc

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity3 : AppCompatActivity() {

    var pantalla: ImageView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        pantalla = findViewById<ImageView>(R.id.imageView2)
        val myUri: String? = intent.getStringExtra("imageUri")
        pantalla?.setImageURI(myUri?.toUri())
    }
}