package com.gusalnim.kotlinsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gusalnim.myfirstapp.R
import kotlinx.android.synthetic.main.kt_layout_binding.*

/**
 * Created by gusalnim on 21/08/2019.
 */

class BmiKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kt_layout_binding)
        button.setOnClickListener {
            val t = tall.text.toString().toDouble()
            val w = weight.text.toString().toDouble()
            val bmi = w / Math.pow(t / 100, 2.0)
            st.text = "키 : ${tall.text}, 체중 : ${weight.text}, BMI : $bmi"
        }
    }

}