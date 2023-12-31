package com.example.gardencalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.maintoolbar))

        val btnCalendar = findViewById<Button>(R.id.buttonCalendar)
        val intentCalendar = Intent(this, Calendar::class.java)
        btnCalendar.setOnClickListener{
            startActivity( intentCalendar, null )
        }

        val btnSettings = findViewById<Button>(R.id.buttonSettings)
        val intentSettings = Intent(this, Settings::class.java)
        btnSettings.setOnClickListener{
            startActivity( intentSettings, null )
        }

        val btnAbout = findViewById<Button>(R.id.buttonAbout)
        val intentAbout = Intent(this, About::class.java)
        btnAbout.setOnClickListener{
            startActivity( intentAbout, null )
        }


    }
}