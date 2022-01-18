package com.tsybulnik.gamememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    lateinit var butStart:Button
    lateinit var butExit:Button
    lateinit var butettings:Button
    lateinit var butScore:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        butStart = findViewById(R.id.butStart)
        butStart.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        butExit = findViewById(R.id.butExit)
        butExit.setOnClickListener {
            finish()
        }
        butettings = findViewById(R.id.butSettings)
        butettings.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
        butScore = findViewById(R.id.butScore)
        butScore.setOnClickListener {
            val intent = Intent(this,HighScoreActivity::class.java)
            startActivity(intent)
        }
    }
}