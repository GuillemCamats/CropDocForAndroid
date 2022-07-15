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
            lgConnection = LgConnection("lg","lqgalaxy","192.168.1.85",22)
            lgConnection!!.connectD()
        }
        relaunch.setOnClickListener{
            lgConnection?.generateAndSendOrbit("0.6017395820287597","41.61585346355983","0","0","5","200")//prage es la altura
        }
        reboot.setOnClickListener{
            lgConnection?.sendKml()
        }
        shutdown.setOnClickListener{
            lgConnection?.sendCommand("/home/lg/bin/lg-poweroff > /home/lg/log.txt")

        }
    }
}
