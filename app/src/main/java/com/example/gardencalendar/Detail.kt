package com.example.gardencalendar


import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Detail : AppCompatActivity() {
    var id: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var toolbar: Toolbar = findViewById(R.id.toolbarDetail)
        setSupportActionBar(toolbar)

        var i : Intent = intent
        id = i.getLongExtra("ID", 0 )

        var db : NoteDatabase = NoteDatabase(this);
        var note : Note = db.getNote(id)
        supportActionBar?.setTitle(note.title)

        var detail: TextView? = findViewById(R.id.noteDetails)
        detail?.setText(note.content)
        detail?.movementMethod = ScrollingMovementMethod()

        var fab : FloatingActionButton = findViewById(R.id.fab)
        fun goToMain() {
            TODO("Not yet implemented")
        }
        fab.setOnClickListener( View.OnClickListener() {
            fun onClick(view: View) {
                var db : NoteDatabase = NoteDatabase(this);
                db.deleteNote(id);
                Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override  fun onOptionsItemSelected(item: MenuItem) : Boolean {
        if (item.itemId == R.id.edit) {
            val i = Intent(this, Edit::class.java)
            i.putExtra("ID", id)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun goToMain() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }


}