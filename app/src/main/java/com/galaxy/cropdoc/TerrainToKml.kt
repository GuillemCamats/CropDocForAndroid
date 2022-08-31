package com.galaxy.cropdoc

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.cropdoc.R

class TerrainToKml : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var setIpToConn: EditText
    lateinit var connect: Button
    lateinit var sendKml: Button
    lateinit var startOrbit: Button
    lateinit var stopOrbit: Button
    lateinit var listKml: ListView
    lateinit var deleteKmls: Button
    var lgConnection: LgConnection? = null
    var terrainName: String ?=null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {// netejar kmls falte
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terrain_to_kml)
        setIpToConn = findViewById(R.id.lg_ip)
        connect = findViewById(R.id.connectKml)
        sendKml = findViewById(R.id.sendKml)
        startOrbit = findViewById(R.id.startOrbit)
        stopOrbit = findViewById(R.id.stopOrbit)
        listKml = findViewById(R.id.list_kmls)
        connect.setBackgroundColor(Color.RED)
        deleteKmls =findViewById(R.id.delete)


        val namesTerrains: ArrayList<String> = arrayListOf()
        for (elem in Terrains.getTerrainsListNames()){
            namesTerrains.add(elem.key)
        }
        val listitems = arrayOfNulls<String>(namesTerrains.size)

        if (namesTerrains.size!=0){
            for (i in 0 until namesTerrains.size){
                val terrain = namesTerrains[i]
                listitems[i] = terrain
            }
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listitems)
        listKml.adapter = adapter

        listKml.choiceMode = ListView.CHOICE_MODE_SINGLE
        listKml.onItemClickListener = this

        connect.setOnClickListener {
            if (!setIpToConn.text.toString().equals(null)){
                lgConnection = LgConnection("lg","lqgalaxy",setIpToConn.text.toString(),22)
                lgConnection!!.connectD(this)
            }
        }

        sendKml.setOnClickListener {
            if (!terrainName.equals(null)){
                val id = Terrains.getTerrainsListNames()[terrainName]
                val terrain = id?.let { it1 -> Terrains.terrainsList.get(it1) }
                lgConnection?.sendKml(terrain)
            }
        }

        startOrbit.setOnClickListener {
            if (!terrainName.equals(null)){
                val id = Terrains.getTerrainsListNames()[terrainName]
                val terrain = id?.let { it1 -> Terrains.terrainsList.get(it1) }
                if (terrain != null) {
                    lgConnection?.generateAndSendOrbit(terrain.trees.get(0).coordinates.latitude.toString(),terrain.trees.get(0).coordinates.longitude.toString(),"0")
                }
            }
            startOrbit.visibility = View.GONE
            stopOrbit.visibility = View.VISIBLE
        }
        stopOrbit.setOnClickListener {
            lgConnection?.stopOrbit()
            stopOrbit.visibility = View.GONE
            startOrbit.visibility = View.VISIBLE
        }
        deleteKmls.setOnClickListener {
            lgConnection?.cleanAll()
        }


    }
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        terrainName =parent?.getItemAtPosition(position) as String
        Toast.makeText(applicationContext,
            "Terrain name:$terrainName",
            Toast.LENGTH_LONG).show()
    }



}