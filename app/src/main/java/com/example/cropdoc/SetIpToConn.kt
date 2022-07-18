package com.example.cropdoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SetIpToConn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_ip_to_conn)
        var lgConnection: LgConnection? = null
        val inputIp = findViewById<EditText>(R.id.ip)
        val buttonIp = findViewById<Button>(R.id.setIp)
        buttonIp.setOnClickListener{
            if(!inputIp.text.toString().equals(null)){
                val ip = inputIp.text.toString()
                lgConnection = LgConnection("lg","lqgalaxy",ip,22)
                lgConnection!!.connectD()
                finish()
            }
        }
    }
}