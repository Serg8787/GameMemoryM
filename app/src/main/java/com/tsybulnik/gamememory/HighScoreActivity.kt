package com.tsybulnik.gamememory

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class HighScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)
        val sharedPref = getSharedPreferences("highScore", Context.MODE_PRIVATE)
        val time = sharedPref.getString("time","0:00")
        val step = sharedPref.getInt("step",1)
        Log.d("score", step.toString())
        Log.d("time", time.toString())
    }
}