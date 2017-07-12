package com.pnote.tw.pocketnote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pnote.tw.pocketnote.utils.NotesListener;
import com.pnote.tw.pocketnote.utils.NotesManager;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ListView notesList = (ListView) findViewById(R.id.notes_list);

        NotesManager notesManager = new NotesManager(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                notesManager.fetchSubjects());

        notesList.setAdapter(arrayAdapter);

        NotesListener notesListener = new NotesListener(this);
        notesList.setOnItemClickListener(notesListener);
    }
}
