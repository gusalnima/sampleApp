package com.gusalnim.kotlinsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gusalnim.myfirstapp.R
import kotlinx.android.synthetic.main.kt_layout_variable.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by gusalnim on 29/08/2019.
 */

class VariableActivity : AppCompatActivity(){

    private var clickCount = 0

    private val startTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kt_layout_variable)

        val timeTxt = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(startTime)

        txt.text = "처리시간  ${timeTxt}"

        count.text = "count : ${clickCount}"

        btn.setOnClickListener {
            clickCount += 1
            count.text = "count :  ${clickCount}"
        }
    }




}