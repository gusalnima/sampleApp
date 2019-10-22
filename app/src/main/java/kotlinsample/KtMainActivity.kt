package com.gusalnim.kotlinsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gusalnim.myfirstapp.R
import kotlinx.android.synthetic.main.kt_activity_main.*

class KtMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kt_activity_main)


        button2.setOnClickListener {
            startActivity(Intent(this@KtMainActivity,BmiKotlinActivity::class.java))
        }

        button3.setOnClickListener {
            startActivity(Intent(this@KtMainActivity,VariableActivity::class.java))
        }
        button4.setOnClickListener {
            startActivity(Intent(this@KtMainActivity,ControllActivity::class.java))
        }

    }
}
