package com.example.cropdoc


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity3 : AppCompatActivity() {

    lateinit var shutdown :Button
    lateinit var reboot :Button
    lateinit var relaunch :Button
    lateinit var connect :Button
    lateinit var setRefresh :Button
    lateinit var resetRefresh :Button
    lateinit var setIp :EditText
    var lgUtils: LgUtils ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        shutdown = findViewById(R.id.shutdown)
        reboot = findViewById(R.id.reboot)
        relaunch = findViewById(R.id.relaunch)
        connect = findViewById(R.id.connect)
        setIp = findViewById(R.id.lg_ip_ac3)
        setRefresh = findViewById(R.id.setRefresh)
        resetRefresh = findViewById(R.id.resetR)

        connect.setOnClickListener{
            if (!setIp.text.toString().equals(null)){
                lgUtils = LgUtils("lg","lqgalaxy",setIp.text.toString(),22)
                //lgConnection!!.connectD()
            }
        }
        relaunch.setOnClickListener{
            lgUtils?.relaunch()
        }
        reboot.setOnClickListener{
            lgUtils?.reboot()
        }
        shutdown.setOnClickListener{
            lgUtils?.shutdow()
        }
        setRefresh.setOnClickListener {
            lgUtils?.setRefresh()
        }
        resetRefresh.setOnClickListener {
            lgUtils?.resetRefresh()
        }

    }
}
