package com.example.volodymyriakymenko.notetaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListNotesActivity extends AppCompatActivity {

    private List<Note> notes = new ArrayList<Note>();
    private ListView notesListView;
    private int editNoteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        notesListView = (ListView) findViewById(R.id.noteListView);

        notes.add(new Note("First note", "yada yada yada", new Date()));
        notes.add(new Note("Second note", "yada yada yada", new Date()));
        notes.add(new Note("Third note", "yada yada yada", new Date()));
        notes.add(new Note("Forth note", "yada yada yada", new Date()));
        notes.add(new Note("Fifth note", "yada yada yada", new Date()));
        populate();

        notesListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int itemNumber, long id) {
                Intent editNoteIntent = new Intent(view.getContext(), NoteEditingActivity.class);
                editNoteIntent.putExtra("Note", notes.get(itemNumber));
                editNoteid = itemNumber;
                startActivityForResult(editNoteIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED){
            return;
        }

        Serializable extra = data.getSerializableExtra("Note");
        if(extra != null){
            Note newNote = (Note)extra;
            if (editNoteid > -1){
                notes.set(editNoteid, newNote);
                editNoteid = -1;
            }else{
                notes.add(newNote);
            }
            populate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_note, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent editNoteIntent = new Intent(this, NoteEditingActivity.class);
        startActivityForResult(editNoteIntent, 1);
        //notes.add(new Note("Added note", "wow", new Date()));
        //populate();
        return super.onOptionsItemSelected(item);
    }

    private void populate() {
        List<String> values = new ArrayList<String>();
        for (Note note: notes){
            values.add(note.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
                android.R.id.text1, values);

        notesListView.setAdapter(adapter);
    }
}
