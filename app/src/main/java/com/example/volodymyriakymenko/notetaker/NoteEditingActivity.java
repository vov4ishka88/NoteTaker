package com.example.volodymyriakymenko.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteEditingActivity extends AppCompatActivity {

    private boolean isInEditMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        final Button saveButton = (Button) findViewById(R.id.saveButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final EditText titleText = (EditText) findViewById(R.id.titleEditText);
        final EditText noteText = (EditText) findViewById(R.id.noteEditText);
        final TextView textView = (TextView) findViewById(R.id.dateTextView);

        Serializable extra = getIntent().getSerializableExtra("Note");
        if(extra != null){
            Note note = (Note)extra;

            titleText.setText(note.getTitle());
            noteText.setText(note.getNote());

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = dateFormat.format(note.getDate());

            textView.setText(date);

            isInEditMode = false;
            titleText.setEnabled(false);
            noteText.setEnabled(false);
            saveButton.setText("Edit");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Moving it to the onCreate method + adding final
                //EditText titleText = (EditText) findViewById(R.id.titleEditText);
                //EditText noteText = (EditText) findViewById(R.id.noteEditText);

                if(isInEditMode)
                {
                    /*
                    isInEditMode = false;
                    saveButton.setText("Edit");
                    titleText.setEnabled(false);
                    noteText.setEnabled(false);

                    TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String date = dateFormat.format(Calendar.getInstance().getTime());
                    dateTextView.setText(date);
                    */
                    Intent returnIntent = new Intent();
                    Note note = new Note(titleText.getText().toString(),
                            noteText.getText().toString(),
                            Calendar.getInstance().getTime());
                    returnIntent.putExtra("Note", note);
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }else
                {
                    isInEditMode = true;
                    saveButton.setText("Save");
                    titleText.setEnabled(true);
                    noteText.setEnabled(true);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
