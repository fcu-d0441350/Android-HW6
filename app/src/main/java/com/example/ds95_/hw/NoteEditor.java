package com.example.ds95_.hw;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteEditor extends AppCompatActivity {

    EditText et_title, et_body;
    ArrayList<String> titlelist;
    SQLiteDatabase db;
    int notepos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        et_title = (EditText)findViewById(R.id.student);
        et_body = (EditText)findViewById(R.id.sclnumber);

        Intent intent = getIntent();
        notepos = intent.getIntExtra("NOTEPOS", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBOpenHelper openhelper = new DBOpenHelper(this);
        db = openhelper.getWritableDatabase();

        titlelist = NoteDB.getTitleList(db);

        if (notepos != -1) {
            String title = titlelist.get(notepos);
            et_title.setText(title);
            et_body.setText(NoteDB.getBody(db, title));
        } else {
            et_title.setText("");
            et_body.setText("");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        String title = et_title.getText().toString();
        if (title.length() == 0) {
            Toast.makeText(this, "沒有輸入任何東西",
                    Toast.LENGTH_LONG).show();
        } else {
            NoteDB.addNote(db, et_title.getText().toString(),
                    et_body.getText().toString());
        }
    }

    boolean isTitleExist(String title) {
        for (int i = 0; i < titlelist.size(); i++)
            if (title.equalsIgnoreCase(titlelist.get(i)))
                return true;
        return false;
    }

}
