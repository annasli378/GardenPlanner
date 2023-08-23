package com.example.gardencalendar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Calendar


class AddNote : AppCompatActivity() {
    lateinit var toolbar: Toolbar;
    lateinit var noteTitle: EditText;
    lateinit var noteDetails: EditText;
    lateinit var c : Calendar;
    lateinit var todaysDate : String;
    lateinit var currentTime : String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        toolbar = findViewById(R.id.toolbarNotes);
        toolbar.setTitleTextColor(resources.getColor(R.color.white));
        setSupportActionBar(toolbar);
        supportActionBar?.setTitle("New Note");
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteTitle=findViewById(R.id.noteTitle);
        noteDetails=findViewById(R.id.noteDetails);

        noteTitle.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.length != 0) {
                    supportActionBar?.setTitle(s)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        // current date & time
        c = Calendar.getInstance()

        todaysDate = (c.get(Calendar.YEAR)).toString()+"/"+(c.get(Calendar.MONTH)+1).toString()+"/"+(c.get(Calendar.DAY_OF_MONTH)).toString();
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE))

        Log.d("test calendar", "Date and Time:    " + todaysDate + ", " + currentTime)
    }

    private fun pad(i: Int): String {
        if(i<10) {
            return "0$i";
        }
        return i.toString();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater;
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            if (noteTitle.text.length != 0) {
                var note: Note = Note(
                    noteTitle.text.toString(),
                    noteDetails.text.toString(),
                    todaysDate,
                    currentTime
                )
                //Log.d("NOTE: ", note.toString())
                var db = NoteDatabase(this);
                var id = db.addNote(note);
                var check: Note = db.getNote(id)
                Log.d("inserted", "Note: " + id.toString() + " -> Title:" + check.getTitle() + " Date: " + check.getDate());

                onBackPressed();
                Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG).show()
                goToMain()
            } else {
                noteTitle.setError("Title Can not be Blank.");
            }
        }
        else if (item.itemId == R.id.delete) {
            //var intentAdd = Intent(this, AddNote::class.java);
            //startActivity(intentAdd);
            Toast.makeText(this, "Note canceled", Toast.LENGTH_LONG).show()
            onBackPressed();
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToMain() {
        var i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}