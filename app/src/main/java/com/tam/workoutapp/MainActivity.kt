package com.tam.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llStart.setOnClickListener {
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        bmiBtn.setOnClickListener {

            val intent = Intent(this,BmiActivity::class.java)
            startActivity(intent)
        }

        bmiHistory.setOnClickListener {

            val intent = Intent(this,History::class.java)
            startActivity(intent)
        }
    }
}