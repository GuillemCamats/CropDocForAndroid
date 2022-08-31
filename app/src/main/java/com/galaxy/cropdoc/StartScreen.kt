package com.galaxy.cropdoc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdoc.R


class StartScreen : AppCompatActivity() {
    lateinit var pantalla:ImageView
    lateinit var logo2: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        pantalla = findViewById(R.id.imageView3)
        logo2 = findViewById(R.id.imageView4)

        pantalla.alpha = 0f
        pantalla.animate()?.setDuration(3000)?.alpha(1f)?.withEndAction{
            val i=Intent(this, MainActivity :: class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
        logo2.alpha = 0f
        logo2.animate()?.setDuration(3000)?.alpha(1f)?.withEndAction{
            val i=Intent(this, MainActivity :: class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

    }
}