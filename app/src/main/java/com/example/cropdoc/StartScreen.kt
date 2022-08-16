package com.example.cropdoc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class StartScreen : AppCompatActivity() {
    private var pantalla: ImageView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        pantalla = findViewById(R.id.imageView3)

        pantalla?.alpha= 0f
        pantalla?.animate()?.setDuration(3000)?.alpha(1f)?.withEndAction{
            val i=Intent(this,MainActivity :: class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

    }
}