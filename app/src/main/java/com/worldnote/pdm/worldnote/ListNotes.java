package com.worldnote.pdm.worldnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.worldnote.pdm.worldnote.model.Note;

import java.util.ArrayList;
import java.util.List;

public class ListNotes extends AppCompatActivity {
    TextView welcome;
    ListView notes_list;
    Button addNote,logout;
    List<Note> notes;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);

        welcome = findViewById(R.id.welcome);
        notes_list = findViewById(R.id.note_list);
        addNote = findViewById(R.id.addNote);
        logout = findViewById(R.id.logout);
        notes = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("notes");

        final FirebaseUser user = mAuth.getCurrentUser();

        welcome.setText("Welcome "+user.getEmail());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notes.clear();

                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){
                    Note note = postSnapshot.getValue(Note.class);
                    if(note.getAuthor().equals(user.getEmail())) {
                        notes.add(note);
                    }
                }

                NotesList notesAdapter = new NotesList(ListNotes.this,notes);

                notes_list.setAdapter(notesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListNotes.this, CreateNote.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent myIntent = new Intent(ListNotes.this, LoginActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });
    }
}
