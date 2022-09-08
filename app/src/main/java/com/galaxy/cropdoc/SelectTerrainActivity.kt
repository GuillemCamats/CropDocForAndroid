package com.galaxy.cropdoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cropdoc.R


class SelectTerrainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var list :ListView
    lateinit var createTerrain: Button
    lateinit var selectTerrain: Button
    var terrainName: String ?=null
    lateinit var back:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_terrain)
        list = findViewById(R.id.terrainsList)
        val foto = intent.getStringExtra("bitmap")
        createTerrain = findViewById(R.id.createTerrain)
        selectTerrain = findViewById(R.id.selectTerrain)
        back = findViewById(R.id.imageButtonSel)
        val prediction = intent.getStringExtra("pred")
        val namesTerrains: ArrayList<String> = arrayListOf()
        back.setOnClickListener {
            onBackPressed()
        }
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


        val adapter = ArrayAdapter<String>(this, R.layout.layout_text_color, listitems)
        list.adapter = adapter

        list.choiceMode = ListView.CHOICE_MODE_SINGLE
        list.onItemClickListener = this

        createTerrain.setOnClickListener{
            val intent = Intent(this, MapsActivityTerrain::class.java)
            intent.putExtra("pred",prediction)
            intent.putExtra("bitmap",foto.toString())
            startActivity(intent)
        }
        selectTerrain.setOnClickListener {
            if(terrainName!=null){
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("name", terrainName)
                intent.putExtra("pos", Terrains.getTerrainsListNames()[terrainName].toString())
                intent.putExtra("pred",prediction)
                intent.putExtra("bitmap",foto.toString())
                startActivity(intent)
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


