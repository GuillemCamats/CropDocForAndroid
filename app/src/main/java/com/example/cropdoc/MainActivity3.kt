package com.example.cropdoc


import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity




class MainActivity3 : AppCompatActivity() {

    var pantalla: ImageView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        pantalla = findViewById<ImageView>(R.id.imageView2)
        val myUri: String? = intent.getStringExtra("bitmap")
        val imageBytes = Base64.decode(myUri, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        pantalla?.setImageBitmap(image)
    }
}
