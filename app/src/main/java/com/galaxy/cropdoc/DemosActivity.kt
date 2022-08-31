package com.galaxy.cropdoc

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.cropdoc.R
import com.google.android.gms.maps.model.LatLng

class DemosActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var setIpToConn: EditText
    lateinit var connect: Button
    lateinit var sendKml: Button
    lateinit var startOrbit: Button
    lateinit var stopOrbit: Button
    lateinit var listKml: ListView
    lateinit var deleteKmls: Button
    var lgDemos: LgDemos? = null
    var terrainName: String ?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
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
        val spain: MutableList<LatLng> = mutableListOf()
        val locSpain: MutableList<Locations> = mutableListOf()
        val japan: MutableList<LatLng> = mutableListOf()
        val locJapan: MutableList<Locations> = mutableListOf()
        val usa: MutableList<LatLng> = mutableListOf()
        val locUsa: MutableList<Locations> = mutableListOf()
        val zealand: MutableList<LatLng> = mutableListOf()
        val locZealand: MutableList<Locations> = mutableListOf()
        val finland: MutableList<LatLng> = mutableListOf()
        val locFinland: MutableList<Locations> = mutableListOf()
        //Terrain in spain
        spain.add(LatLng(41.61674609676081,0.6015484093490131))
        spain.add(LatLng(41.6154898455284,0.6012436548527544))
        spain.add(LatLng(41.61541507837795,0.6018376622060395))
        spain.add(LatLng(41.61671301988138,0.6021720468527714))
        locSpain.add(Locations(LatLng(41.61645934147053,0.6017831774014515),"Type: Healty | Accuracy: 0.98",null))
        locSpain.add(Locations(LatLng(41.61612378517063,0.6015433454355446),"Type: Scab | Accuracy: 0.95",null))
        locSpain.add(Locations(LatLng(41.61583622714437,0.6018068961942613),"Type: Powdery Mildew | Accuracy: 0.97",null))
        locSpain.add(Locations(LatLng(41.61559670827007,0.6014367017521005),"Type: Rust | Accuracy: 0.93",null))
        Terrains.demosList.add(Terrains("Spain-Catalonia",spain,locSpain))
        //Terrain in japan
        japan.add(LatLng(43.31357263256779,142.5268470810903))
        japan.add(LatLng(43.31032468838995,142.5274771524065))
        japan.add(LatLng(43.31105490280981,142.5299399940624))
        japan.add(LatLng(43.31394403824977,142.5300461595126))
        locJapan.add(Locations(LatLng(43.31323289387427,142.527544496963),"Type: Rust | Accuracy: 0.93",null))
        locJapan.add(Locations(LatLng(43.31344199302715,142.5294451319421),"Type: Healty | Accuracy: 0.98",null))
        locJapan.add(Locations(LatLng(43.3122962050514,142.5277512452663),"Type: Scab | Accuracy: 0.95",null))
        locJapan.add(Locations(LatLng(43.31245750921763,142.5294677755645),"Type: Powdery Mildew | Accuracy: 0.97",null))
        locJapan.add(Locations(LatLng(43.31175005425296,142.5295233622719),"Type: Frog eye leaf spot | Accuracy: 0.91",null))
        locJapan.add(Locations(LatLng(43.31087942208211,142.5280103457515),"Type: Complex | Accuracy: 0.85",null))
        Terrains.demosList.add(Terrains("Japan-Hokkaido",japan,locJapan))
        //Terrain in USA
        usa.add(LatLng(47.71394492773268,-122.1502640935128))
        usa.add(LatLng(47.71157503337732,-122.1503847762953))
        usa.add(LatLng(47.71152309286038,-122.1434771391116))
        usa.add(LatLng(47.71387101145826,-122.1435135363966))
        locUsa.add(Locations(LatLng(47.71332625200721,-122.1445247277667),"Type: Rust | Accuracy: 0.93",null))
        locUsa.add(Locations(LatLng(47.71206453810439,-122.1445587367912),"Type: Healty | Accuracy: 0.98",null))
        locUsa.add(Locations(LatLng(47.71353380431066,-122.146153668628),"Type: Scab | Accuracy: 0.95",null))
        locUsa.add(Locations(LatLng(47.71231929941272,-122.1465401414568),"Type: Powdery Mildew | Accuracy: 0.97",null))
        locUsa.add(Locations(LatLng(47.71365147929809,-122.1481958723549),"Type: Frog eye leaf spot | Accuracy: 0.91",null))
        locUsa.add(Locations(LatLng(47.71284561119144,-122.1493678142078),"Type: Complex | Accuracy: 0.85",null))
        Terrains.demosList.add(Terrains("USA-Seattle",usa,locUsa))
        //Terrain in New Zealand
        zealand.add(LatLng(-43.54637896804826,172.5858705516818))
        zealand.add(LatLng(-43.54684482536258,172.5842954102189))
        zealand.add(LatLng(-43.54888359262839,172.5855833676535))
        zealand.add(LatLng(-43.54862848259241,172.5861012731277))
        zealand.add(LatLng(-43.54727500634963,172.5856624452496))
        zealand.add(LatLng(-43.54682194061562,172.5863799744803))
        locZealand.add(Locations(LatLng(-43.54673717606823,172.5858611883654),"Type: Rust | Accuracy: 0.93",null))
        locZealand.add(Locations(LatLng(-43.54682958179511,172.5850637623444),"Type: Powdery Mildew | Accuracy: 0.97",null))
        locZealand.add(Locations(LatLng(-43.54741414229032,172.5850613246266),"Type: Healty | Accuracy: 0.98",null))
        locZealand.add(Locations(LatLng(-43.54770692054761,172.5854762070076),"Type: Complex | Accuracy: 0.85",null))
        locZealand.add(Locations(LatLng(-43.54828637768781,172.5855657376412),"Type: Scab | Accuracy: 0.95",null))
        Terrains.demosList.add(Terrains("New Zealand-Christchurch",zealand,locZealand))
        //Terrain in Finland
        finland.add(LatLng(60.25766065253077,24.7432888855303))
        finland.add(LatLng(60.25895959053873,24.74175857044142))
        finland.add(LatLng(60.25812241166738,24.7379401491765))
        finland.add(LatLng(60.25640491547207,24.74224147506928))
        locFinland.add(Locations(LatLng(60.25856453162532,24.74144508630802),"Type: Scab | Accuracy: 0.95",null))
        locFinland.add(Locations(LatLng(60.25773859630054,24.74246759925584),"Type: Powdery Mildew | Accuracy: 0.97",null))
        locFinland.add(Locations(LatLng(60.2582093379926,24.74069158239971),"Type: Healty | Accuracy: 0.98",null))
        locFinland.add(Locations(LatLng(60.25740335908996,24.74172446982271),"Type: Rust | Accuracy: 0.99",null))
        locFinland.add(Locations(LatLng(60.25797647644052,24.73948294179837),"Type: Frog eye leaf spot | Accuracy: 0.91",null))
        locFinland.add(Locations(LatLng(60.25713208640494,24.74111876958704),"Type: Powdery Mildew | Accuracy: 0.97",null))
        Terrains.demosList.add(Terrains("Finland-Helsinki",finland,locFinland))

        //end of samples

        val namesTerrains: ArrayList<String> = arrayListOf()
        for (elem in Terrains.getTerrainsListNamesDemos()){
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
                println(setIpToConn.text.toString())
                lgDemos = LgDemos("lg","lqgalaxy",setIpToConn.text.toString(),22)
                lgDemos!!.connectD()
            }
        }

        sendKml.setOnClickListener {
            if (!terrainName.equals(null)){
                val id = Terrains.getTerrainsListNamesDemos()[terrainName]
                val terrain = id?.let { it1 -> Terrains.demosList.get(it1) }
                lgDemos?.sendKml(terrain)
            }
        }

        startOrbit.setOnClickListener {
            if (!terrainName.equals(null)){
                val id = Terrains.getTerrainsListNamesDemos()[terrainName]
                val terrain = id?.let { it1 -> Terrains.demosList.get(it1) }
                if (terrain != null) {
                    lgDemos?.generateAndSendOrbit(terrain.trees.get(0).coordinates.latitude.toString(),terrain.trees.get(0).coordinates.longitude.toString(),"0")
                }
            }
            startOrbit.visibility = View.GONE
            stopOrbit.visibility = View.VISIBLE
        }
        stopOrbit.setOnClickListener {
            lgDemos?.stopOrbit()
            stopOrbit.visibility = View.GONE
            startOrbit.visibility = View.VISIBLE
        }
        deleteKmls.setOnClickListener {
            lgDemos?.cleanAll()
        }

    }
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        terrainName =parent?.getItemAtPosition(position) as String
        Toast.makeText(applicationContext,
            "Terrain name:$terrainName",
            Toast.LENGTH_LONG).show()
    }

}