package com.pnote.tw.pocketnote.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class NotesManager {

    private Activity activity;

    private static String FILENAME = "notes.json";

    private JSONArray fetchNotes() throws IOException, org.json.simple.parser.ParseException {
        FileInputStream fileNotes = activity.openFileInput(FILENAME);

        JSONParser jsonParser = new JSONParser();

        return (JSONArray) jsonParser.parse(new FileReader(fileNotes.getFD()));
    }

    public NotesManager(Activity activity) {
        this.activity = activity;
    }

    public ArrayList fetchSubjects() {
        ArrayList subjects = new ArrayList();
        try {

            JSONArray notes = fetchNotes();

            for (Object note : notes) {
                JSONObject next = (JSONObject) note;

                subjects.add(next.get("subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public void writeNote(String subject, String content) throws IOException, ParseException, org.json.simple.parser.ParseException {

        JSONParser parser = new JSONParser();

        try {
            FileInputStream fis = activity.openFileInput(FILENAME);
            FileReader notesReader = new FileReader(fis.getFD());

            JSONArray presentNotes = (JSONArray) parser.parse(notesReader);

            JSONObject note = new JSONObject();
            note.put("subject", subject);
            note.put("content", content);

            presentNotes.add(note);

            FileOutputStream fos = activity.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            fos.write(presentNotes.toJSONString().getBytes());
            Log.i("Successfully wrote!", FILENAME);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(int noteId, String subject, String content) throws IOException, org.json.simple.parser.ParseException {
        Log.i("Note id: ", String.valueOf(noteId));
        JSONArray notes = fetchNotes();
        JSONObject noteToUpdate = (JSONObject) notes.get(noteId);

        noteToUpdate.put("subject", subject);
        noteToUpdate.put("content", content);

        FileOutputStream fos = activity.openFileOutput(FILENAME, Context.MODE_PRIVATE);

        fos.write(notes.toJSONString().getBytes());
        fos.close();
    }

    protected String fetchContent(long id) throws IOException, org.json.simple.parser.ParseException {
        String content = "";
        try {

            JSONArray notes = fetchNotes();

            JSONObject note = (JSONObject) notes.get((int) id);
            content = (String) note.get("content");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
