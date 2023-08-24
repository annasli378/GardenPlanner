package com.example.gardencalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {
    // declare require values
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "NoteDB";
    private static final String TABLE_NAME = "NoteTable";

    public NoteDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // declare table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE "+TABLE_NAME+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT,"+
                KEY_TIME+" TEXT"
                +" )";
        db.execSQL(createDb);
    }

    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // values:
        cv.put(KEY_TITLE,note.getTitle());
        cv.put(KEY_CONTENT,note.getContent());
        cv.put(KEY_DATE,note.getDate());
        cv.put(KEY_TIME,note.getTime());

        // inserting data into db
        long ID = db.insert(TABLE_NAME,null,cv);
        return  ID;
    }

    public Note getNote(long id){
        // select * from table where id == <ID>
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        Cursor cursor = db.query(TABLE_NAME,columns,KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        Note note = new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        cursor.close();
        return note;

    }

    public List<Note> getAllNotes(){
        List<Note> allNotes = new ArrayList<>();
        // select * from table
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        /*if (cursor != null) {
            cursor.moveToFirst();
            do{
                Note note = new Note();
                //note.setID(Long.parseLong(cursor.getString(0)));
                note.setID(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));

                allNotes.add(note);

            }while (cursor.moveToNext());
        } else {
            Log.d("WARNING: " , "NO values in database");
        }
        Log.d("NOTE_size", String.valueOf(allNotes.size()));
        return allNotes;*/

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Long.parseLong(cursor.getString(0)));
                //note.setID(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));

                allNotes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("notes", String.valueOf(allNotes.size()));
        return allNotes;


    }

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getID());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        return db.update(TABLE_NAME,c,KEY_ID+"=?",new String[]{String.valueOf(note.getID())});
    }

    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
