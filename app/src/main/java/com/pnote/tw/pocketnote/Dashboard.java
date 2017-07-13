package com.pnote.tw.pocketnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pnote.tw.pocketnote.utils.NotesListener;
import com.pnote.tw.pocketnote.utils.NotesManager;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ListView notes = (ListView) findViewById(R.id.notes_list);

        NotesManager notesManager = new NotesManager(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notesManager.fetchSubjects());

        notes.setAdapter(arrayAdapter);

        NotesListener notesListener = new NotesListener(this);
        notes.setOnItemClickListener(notesListener);
    }

    public void addNewNote(View view) {
        Intent addNoteIntent = new Intent(this, SaveNote.class);
        startActivity(addNoteIntent);
    }
}
