package com.example.gardencalendar

import android.R.layout
import android.R.menu
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class Edit : AppCompatActivity() {
    var toolbar: Toolbar? = null
    var nTitle: EditText? = null
    var nContent:EditText? = null
    var c: java.util.Calendar? = null
    var todaysDate: String? = null
    var currentTime: String? = null
    var nId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        toolbar = findViewById(R.id.toolbarNotes)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeButtonEnabled(true)

        val i = intent
        nId = i.getLongExtra("ID", 0)
        val db = NoteDatabase(this)
        val note: Note = db.getNote(nId)

        val title = note.title
        val content = note.content
        nTitle = findViewById<EditText>(R.id.noteTitle)
        nContent = findViewById<EditText>(R.id.noteDetails)

        nTitle?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                supportActionBar!!.title = title
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 0) {
                    supportActionBar!!.title = s
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        nTitle?.setText(title)
        nContent?.setText(content)

        c = java.util.Calendar.getInstance()

        todaysDate = (c?.get(java.util.Calendar.YEAR)).toString()+"/"+(c?.get(java.util.Calendar.MONTH)
            ?.plus(1)).toString()+"/"+(c?.get(
            java.util.Calendar.DAY_OF_MONTH)).toString()
        currentTime = c?.get(java.util.Calendar.HOUR)?.let { pad(it) } +":"+ c?.get(java.util.Calendar.MINUTE)
            ?.let { pad(it) }

        Log.d("test calendar", "Date and Time:    " + todaysDate + ", " + currentTime)

    }

    private fun pad(i: Int): String {
        if(i<10) {
            return "0$i"
        }
        return i.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            val note = Note(
                nId,
                nTitle!!.text.toString(),
                nContent!!.text.toString(),
                todaysDate,
                currentTime
            )
            Log.d("EDITED", "edited: before saving id -> " + note.id)
            val sDB = NoteDatabase(applicationContext)
            val id: Int = sDB.editNote(note)
            Log.d("EDITED", "EDIT: id $id")
            goToMain()
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.delete) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToMain() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }


}