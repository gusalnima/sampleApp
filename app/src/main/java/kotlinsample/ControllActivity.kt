package com.gusalnim.kotlinsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gusalnim.myfirstapp.R
import kotlinx.android.synthetic.main.kt_layout_controll.*

/**
 * Created by gusalnim on 29/08/2019.
 */
class ControllActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kt_layout_controll)
        btn.setOnClickListener {
            val number = number.text.toString().toInt()

            when {
                number %2 ==0 -> {
                    Toast.makeText(this, "${number}는 2의 배수입니다.", Toast.LENGTH_SHORT).show()

                }
                number %3 == 0 -> {
                    Toast.makeText(this, "${number}는 3의 배수입니다.", Toast.LENGTH_SHORT).show()
                }
                else ->
                    Toast.makeText(this, "${number}", Toast.LENGTH_SHORT).show()
            }

            when(number){
                in 1..4 -> btn.text = "실행 - 4"
                9,18 ->btn.setText("실행 -9 ")
                else -> btn.text = "실행"
            }
        }
    }
}