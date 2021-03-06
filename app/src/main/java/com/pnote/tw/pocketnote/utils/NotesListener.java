package com.pnote.tw.pocketnote.utils;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.pnote.tw.pocketnote.SaveNote;

public class NotesListener implements AdapterView.OnItemClickListener {
    private Activity activity;

    public NotesListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            NotesManager notesManager = new NotesManager(activity);
            notesManager.fetchContent((int) id);

            Intent updateNoteIntent = new Intent(activity, SaveNote.class);
            updateNoteIntent.putExtra("noteId", id);
            activity.startActivity(updateNoteIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
