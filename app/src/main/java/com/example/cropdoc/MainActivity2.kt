package com.example.cropdoc

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdoc.databinding.ActivityMain2Binding
import com.example.cropdoc.ml.LiteModelDiseaseClassification1
import org.tensorflow.lite.support.image.TensorImage


class MainActivity2 : AppCompatActivity(){
    private var pantalla: ImageView ?= null
    private lateinit var binding: ActivityMain2Binding
    var confirm: Button ?=null
    var data: Uri ?= null
    var text: TextView ?= null
    lateinit var takePicture: Button
    private var bitmap: Bitmap ?= null
    var prediction: String ?= null



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        title = "KotlinApp"
        pantalla = findViewById(R.id.imageView)
        val buttonClick = findViewById<Button>(R.id.buttonLoadPicture)
        confirm = findViewById(R.id.buttonConfirm)
        val location = findViewById<Button>(R.id.add_location)

        buttonClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        confirm?.setOnClickListener {
            val model = LiteModelDiseaseClassification1.newInstance(this)
            text = findViewById(R.id.textView)

            bitmap = bitmap?.copy(Bitmap.Config.ARGB_8888, true)
            //val resized: Bitmap? = bitmap?.let { it1 -> Bitmap.createScaledBitmap(it1, 1, 10, false) }


            val tfImage = TensorImage.fromBitmap(bitmap)

            //val byteBuffer = tfImage.buffer
            //val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1,10), DataType.FLOAT32)

            //Log.d("shape", byteBuffer.toString())
            //Log.d("shape", inputFeature0.buffer.toString())
            //inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(tfImage)
            val probability = outputs.probabilityAsCategoryList.apply { sortByDescending { it.score } }
            prediction = stringSelector(probability.toString())
            text?.text = prediction
            location.visibility = View.VISIBLE
        }

        location.setOnClickListener{
            val intent = Intent(this@MainActivity2, SelectTerrainActivity::class.java)
            intent.putExtra("pred",prediction)
            Log.d("pred",prediction.toString())
            intent.putExtra("bitmap",bitmap.toString())
            startActivity(intent)
        }


    }

    fun stringSelector(s: String): String {

        val typeplant = s.drop(15)

        val parts = typeplant.split("(", ")", "\"", "=")

        val tipo = parts[0]
        val score = parts[6]

        return "Type: $tipo | Accuracy: $score"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            confirm?.isEnabled = true
            data = result.data?.data
            val source: ImageDecoder.Source? = data?.let { ImageDecoder.createSource(this.contentResolver, it) }
            bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
            pantalla?.setImageBitmap(bitmap)
        }
    }

}