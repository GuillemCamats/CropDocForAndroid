package com.example.cropdoc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class SetIpToConn : AppCompatActivity() {
    var main3: MainActivity3 ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_ip_to_conn)
        val inputIp = findViewById<EditText>(R.id.ip)
        val buttonIp = findViewById<Button>(R.id.setIp)
        buttonIp.setOnClickListener{
            if(!inputIp.text.toString().equals(null)){
                val ip = inputIp.text.toString()
                main3?.connectip(ip)
                Log.d("conn",ip)
                finish()
            }
        }
    }
}