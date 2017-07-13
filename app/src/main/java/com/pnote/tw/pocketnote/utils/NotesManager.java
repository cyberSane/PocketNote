package com.pnote.tw.pocketnote.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NotesManager {

    private Activity activity;

    private static String FILENAME = "NOTES_COLLECTION";

    public NotesManager(Activity activity) {
        this.activity = activity;
    }

    public ArrayList fetchSubjects() {
        JSONParser jsonParser = new JSONParser();
        ArrayList subjects = new ArrayList();
        try {
            HashMap<String, String> notes = fetchNotes();

            for (int i = 0; i < notes.size(); i++) {
                String note = notes.get(String.valueOf(i));

                JSONObject parsedNote = (JSONObject) jsonParser.parse(note);

                subjects.add(parsedNote.get("subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public JSONObject fetchNote(long noteId) throws IOException, org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        HashMap<String, String> notes = fetchNotes();

        JSONObject filteredNote = (JSONObject) jsonParser.parse(notes.get(String.valueOf(noteId)));

        return (JSONObject) filteredNote.clone();
    }

    public int notesCount() throws IOException, org.json.simple.parser.ParseException {
        return fetchNotes().size();
    }

    public void saveNote(int noteId, String subject, String content) throws IOException, org.json.simple.parser.ParseException {
        JSONObject note = new JSONObject();

        note.put("subject", subject);
        note.put("content", content);

        SharedPreferences sharedPreferences = activity.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(String.valueOf(noteId), note.toJSONString());

        editor.apply();
    }

    String fetchContent(int noteId) throws IOException, org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        HashMap<String, String> notes = fetchNotes();

        JSONObject note = (JSONObject) jsonParser.parse(notes.get(String.valueOf(noteId)));

        return note.get("content").toString();
    }

    private HashMap<String, String> fetchNotes() throws IOException, org.json.simple.parser.ParseException {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

        return (HashMap<String, String>) sharedPreferences.getAll();
    }
}
