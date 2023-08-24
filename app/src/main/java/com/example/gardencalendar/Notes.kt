package com.example.gardencalendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager


class Notes : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var noItem: TextView
    private lateinit var db: NoteDatabase
    //private lateinit var allNotesList : List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        toolbar = findViewById(R.id.toolbarNotes)
        setSupportActionBar(toolbar)

        noItem = findViewById(R.id.noItemText)
        db = NoteDatabase(this)

        recyclerView = findViewById(R.id.listOfNotes)

       /* recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(this, allNotesList)
        recyclerView.adapter = adapter
        */

        val allNotesList : List<Note> = db.allNotes

        if(allNotesList.isEmpty()){
            noItem.visibility = View.VISIBLE
            Log.d("DB" , " Database is empty")
        }else {
            noItem.visibility = View.GONE
            displayList(allNotesList)
        }

    }

    private fun displayList(allNotesList: List<Note>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(this, allNotesList)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            Toast.makeText(this, "Add New Note", Toast.LENGTH_SHORT).show()
            val intentAdd = Intent(this, AddNote::class.java)
            startActivity(intentAdd)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val getAllNotes: List<Note> = db.allNotes
        if (getAllNotes.isEmpty()) {
            noItem.visibility = View.VISIBLE
        } else {
            noItem.visibility = View.GONE
            displayList(getAllNotes)
        }
    }

}