package com.worldnote.pdm.worldnote;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.worldnote.pdm.worldnote.model.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        welcome.setText("Welcome  "+user.getEmail());

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

        notes_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = notes.get(i);
                Log.v("teste","4");
                showUpdateDeleteDialog(note.getNoteId());
                Log.v("teste","5");
                return true;
            }
        });
    }
    private boolean updateNote(final String id, final String title, final String note, final String date){
       final DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes");
        dR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> postValues = new HashMap<String, Object>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    postValues.put(snapshot.getKey(),snapshot.getValue());
                }
                postValues.put("title",title);
                postValues.put("component",note);
                postValues.put("date",date);
                Toast.makeText(getApplicationContext(), "Note Updated", Toast.LENGTH_LONG).show();
                dR.child("notes").child(id).updateChildren(postValues);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       //Note nNova = new Note(title,note,date);
       //dR.setValue(nNova);
        return true;
    }
    private void showUpdateDeleteDialog(final String noteid){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        Log.v("teste","6");
        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);
        Log.v("teste","7");
        final EditText title = dialogView.findViewById(R.id.title);
        final EditText note = dialogView.findViewById(R.id.note);
        final EditText date = dialogView.findViewById(R.id.date);
        final Button editar = dialogView.findViewById(R.id.edit);
        final Button deletar = dialogView.findViewById(R.id.delete);

        final AlertDialog b = dialogBuilder.create();
        Log.v("teste","8");
        b.show();
        Log.v("teste","9");
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = title.getText().toString();
                String nota = note.getText().toString();
                String data = date.getText().toString();

                if(!TextUtils.isEmpty(titulo)){
                    updateNote(noteid,titulo,nota,data);
                    b.dismiss();
                }
            }
        });
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
