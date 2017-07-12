package com.pnote.tw.pocketnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.pnote.tw.pocketnote.utils.NotesManager;

import java.io.IOException;
import java.text.ParseException;

public class UpdateNote extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
    }

    public void update(View view) throws IOException, ParseException, org.json.simple.parser.ParseException {
        Intent updateIntent = getIntent();
        long noteId = updateIntent.getLongExtra("noteId", 0);
        EditText contentArea = (EditText) findViewById(R.id.content_area);
        EditText subjectArea = (EditText) findViewById(R.id.subject_area);

        NotesManager notesManager = new NotesManager(this);

        notesManager.updateNote((int) noteId, subjectArea.getText().toString(), contentArea.getText().toString());


        Intent dashboardIntent = new Intent(this, Dashboard.class);
        startActivity(dashboardIntent);
    }
}
