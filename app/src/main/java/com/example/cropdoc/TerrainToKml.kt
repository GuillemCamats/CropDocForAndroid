package com.example.cropdoc

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class TerrainToKml : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var setIpToConn: EditText
    lateinit var connect: Button
    lateinit var sendKml: Button
    lateinit var startOrbit: Button
    lateinit var stopOrbit: Button
    lateinit var listKml: ListView
    var lgConnection: LgConnection? = null
    var terrainName: String ?=null

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
            }
        }

        sendKml.setOnClickListener {
            if (!terrainName.equals(null)){
                val id = Terrains.getTerrainsListNames()[terrainName]
                val terrain = id?.let { it1 -> Terrains.terrainsList.get(it1) }
                lgConnection?.sendKml(terrain)
            }
        }


    }
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        terrainName =parent?.getItemAtPosition(position) as String
        Toast.makeText(applicationContext,
            "Terrain name:$terrainName",
            Toast.LENGTH_LONG).show()
    }



}