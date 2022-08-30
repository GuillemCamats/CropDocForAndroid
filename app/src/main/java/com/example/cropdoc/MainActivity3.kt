package com.example.cropdoc


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
                lgUtils!!.connectD()
            }
        }
        relaunch.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Relaunch?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    lgUtils?.relaunch()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        reboot.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Reboot?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    lgUtils?.setRefresh()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        shutdown.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Shutdown?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    lgUtils?.shutdow()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        setRefresh.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to setRefresh?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    lgUtils?.setRefresh()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

        }
        resetRefresh.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to resetRefresh?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    lgUtils?.resetRefresh()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }
}
