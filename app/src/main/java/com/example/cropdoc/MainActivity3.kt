package com.example.cropdoc


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


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
            lgConnection = LgConnection("lg","lqgalaxy","192.168.1.85",22)
            lgConnection!!.connectD()
        }
        relaunch.setOnClickListener{
            lgConnection?.sendCommand("/home/lg/bin/lg-relaunch > /home/lg/log.txt")
        }
        reboot.setOnClickListener{
            lgConnection?.sendCommand("/home/lg/bin/lg-reboot > /home/lg/log.txt")
        }
        shutdown.setOnClickListener{
            lgConnection?.sendCommand("/home/lg/bin/lg-poweroff > /home/lg/log.txt")
        }
    }
}
