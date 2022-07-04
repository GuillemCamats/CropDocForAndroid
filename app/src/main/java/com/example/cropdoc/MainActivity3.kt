package com.example.cropdoc


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity




class MainActivity3 : AppCompatActivity() {

    lateinit var shutdown :Button
    lateinit var reboot :Button
    lateinit var relaunch :Button
    lateinit var connect :Button
    var lgConnection: LgConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        shutdown = findViewById(R.id.shutdown)
        reboot = findViewById(R.id.reboot)
        relaunch = findViewById(R.id.relaunch)
        connect = findViewById(R.id.connect)

        connect.setOnClickListener{
            lgConnection = LgConnection("lg","lqgalaxy","192.168.1.84",22)
            lgConnection!!.connectD()
        }
        relaunch.setOnClickListener{
            lgConnection?.sendCommand("lg-relaunch")
        }
        reboot.setOnClickListener{
            lgConnection?.sendCommand("lg-reboot")
        }
        shutdown.setOnClickListener{
            lgConnection?.sendCommand("lg-poweroff")
        }
    }
}
