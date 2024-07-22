package com.example.bmicalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var resultText: TextView
    private lateinit var calculateButton: Button
    private lateinit var bmiNumber: TextView
    private lateinit var bmiComment: String
    private lateinit var cardViewResult: CardView
    private var bmiValue: Double = 0.0
    private var weight: Double = 0.0
    private var height: Double = 0.0

    private fun calculateBMI(weight: Double, height: Double): Double {
        return weight / ((height / 100) * (height / 100))
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        weightEditText = findViewById(R.id.weightEditText)
        heightEditText = findViewById(R.id.heightEditText)
        calculateButton = findViewById(R.id.btnCalculator)
        bmiNumber = findViewById(R.id.bmiNumber)
        calculateButton.setOnClickListener {
            if(weightEditText.text.toString() == ""){
                Toast.makeText(this, "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show()
            }
            else if(heightEditText.text.toString() == ""){
                Toast.makeText(this, "Vui lòng nhập chiều cao", Toast.LENGTH_SHORT).show()
            }
            else {
                weight = weightEditText.text.toString().toDouble()
                height = heightEditText.text.toString().toDouble()
                bmiValue = calculateBMI(weight, height)
                bmiNumber.text = stringResultBMI(String.format("%.2f", bmiValue))
                var color = 0
                when {
                    bmiValue < 18.5 ->{
                        bmiComment = "Thiếu cân"
                        color = R.color.gay_go
                    }
                    bmiValue in 18.5..24.9 ->{
                        bmiComment = "Bình thường"
                        color = R.color.binh_thuong
                    }
                    bmiValue in 25.0..29.9 ->{
                        bmiComment = "Thừa cân"
                        color = R.color.thua_can
                    }
                    else ->{
                        bmiComment = "Béo phì"
                        color = R.color.beo_phi

                    }
                }
                resultText = findViewById(R.id.bmiComment)
                resultText.text = bmiComment
                cardViewResult = findViewById(R.id.bmiResult)
                cardViewResult.visibility = CardView.VISIBLE
                if(color != 0){
                    resultText.setTextColor(ContextCompat.getColor(this, color))
                }
                if(bmiValue >= 29.9){
                    sleep(2000)
                    openUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                }
            }
        }
    }

    fun stringResultBMI(bmi: String): String{
        return "BMI: $bmi"
    }

}