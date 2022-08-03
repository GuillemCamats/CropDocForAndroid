package com.example.cropdoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView


class SelectTerrainActivity : AppCompatActivity() {
    lateinit var list :ListView
    lateinit var createTerrain: Button
    lateinit var selectTerrain: Button
    var item: Terrains ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_terrain)
        list = findViewById(R.id.terrainsList)
        createTerrain = findViewById(R.id.createTerrain)
        selectTerrain = findViewById(R.id.selectTerrain)
        val arrayAdapter: ArrayAdapter<*>
        val namesTerrains = arrayOf(Terrains.getTerrainsListNames())
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, namesTerrains)
        list.adapter = arrayAdapter

        list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // This is your listview's selected item
            item = parent.getItemAtPosition(position) as Terrains
        }
        createTerrain.setOnClickListener{
            val intent = Intent(this,MapsActivityTerrain::class.java)
            startActivity(intent)
        }
        selectTerrain.setOnClickListener {
            if(item!=null){
                val intent = Intent(this,MapsActivity::class.java)
                intent.putExtra("name", item!!.name)
                startActivity(intent)
            }
        }
    }// agafar el item i passarlo a la seguent activitat sigui, crear un nou terreno, o que hagi seleccional el terreno, i guardarho, i desde el nom s'hafegeix el marcador al terreny corresponent.

}