package com.example.cropdoc

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdoc.ml.LiteModelDiseaseClassification1
import com.example.cropdoc.ml.Modelpocho
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer



class MainActivity2 : AppCompatActivity() {
    var pantalla: ImageView ?= null
    var confirm: Button ?=null
    var data: Uri ?= null
    var text: TextView ?= null
    private var bitmap: Bitmap ?= null
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        title = "KotlinApp"
        pantalla = findViewById(R.id.imageView)
        val buttonClick = findViewById<Button>(R.id.buttonLoadPicture)
        confirm = findViewById(R.id.buttonConfirm)

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

            text?.text = probability.toString()


        }
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