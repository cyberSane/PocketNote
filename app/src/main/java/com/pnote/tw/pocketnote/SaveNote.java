package com.pnote.tw.pocketnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.pnote.tw.pocketnote.utils.NotesManager;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.ParseException;

public class SaveNote extends AppCompatActivity {

    public static final int THRESHOLD_VALUE = 999999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        populateNote();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Dashboard.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateNote() {
        try {
            Intent saveIntent = getIntent();
            boolean isNoteIdExist = saveIntent.hasExtra("noteId");
            JSONObject note = new JSONObject();

            if (isNoteIdExist) {
                long noteId = saveIntent.getLongExtra("noteId", THRESHOLD_VALUE);

                NotesManager notesManager = new NotesManager(this);

                note = notesManager.fetchNote(noteId);
            } else {
                note.put("subject", "");
                note.put("content", "");
            }

            EditText contentArea = (EditText) findViewById(R.id.content_area);
            EditText subjectArea = (EditText) findViewById(R.id.subject_area);

            subjectArea.setText(note.get("subject").toString());
            contentArea.setText(note.get("content").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(View view) throws IOException, ParseException, org.json.simple.parser.ParseException {
        Intent saveIntent = getIntent();
        EditText contentArea = (EditText) findViewById(R.id.content_area);
        EditText subjectArea = (EditText) findViewById(R.id.subject_area);

        NotesManager notesManager = new NotesManager(this);

        int currentCount = (int) saveIntent.getLongExtra("noteId", notesManager.notesCount());

        notesManager.saveNote(currentCount, subjectArea.getText().toString(), contentArea.getText().toString());

        Intent dashboardIntent = new Intent(this, Dashboard.class);
        startActivity(dashboardIntent);
    }
}
