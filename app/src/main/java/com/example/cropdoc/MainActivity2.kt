package com.example.cropdoc

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import kotlin.system.exitProcess


class MainActivity2 : AppCompatActivity() {
    var pantalla: ImageView ?= null
    var imageSelected: Boolean ?= false
    var confirm: Button ?=null
    var data: Uri ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        title = "KotlinApp"
        pantalla = findViewById<ImageView>(R.id.imageView)
        val buttonClick = findViewById<Button>(R.id.buttonLoadPicture)
        confirm = findViewById<Button>(R.id.buttonConfirm)

        buttonClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        confirm?.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("imageUri",data.toString())
            startActivity(intent)
        }

    }
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            confirm?.visibility = VISIBLE
            data = result.data?.data
            pantalla?.setImageURI(data)
        }
    }
}