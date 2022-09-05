package com.galaxy.cropdoc

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
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
import com.example.cropdoc.R
import com.example.cropdoc.databinding.ActivityMain2Binding
import com.example.cropdoc.ml.LiteModelDiseaseClassification1
import com.example.cropdoc.ml.Effi85
import com.example.cropdoc.ml.Mobile85
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.gpu.CompatibilityList
import java.io.File


class MainActivity2 : AppCompatActivity(){
    private var pantalla: ImageView ?= null
    private lateinit var binding: ActivityMain2Binding
    var confirm: Button ?=null
    var data: Uri ?= null
    var text: TextView ?= null
    private var bitmap: Bitmap ?= null
    var prediction: String ?= null
    var f: File? = null


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
            val model = Mobile85.newInstance(this)
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
            intent.putExtra("bitmap",f.toString())
            startActivity(intent)
        }


    }

    fun stringSelector(s: String): String {

        Log.d("s",s)
        val typeplant = s.drop(11)

        val parts = typeplant.split("(", ")", "\"", "=")
        Log.d("s",parts.toString())
        val tipo = parts[1]
        val score = parts[7]

        return "Type: $tipo | Accuracy: $score"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            confirm?.isEnabled = true
            data = result.data?.data
            f = data?.path?.let { File(it) }
            val source: ImageDecoder.Source? = data?.let { ImageDecoder.createSource(this.contentResolver, it) }
            bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
            pantalla?.setImageBitmap(bitmap)
        }
    }

}