package com.worldnote.pdm.worldnote;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.worldnote.pdm.worldnote.model.Note;

import java.util.HashMap;
import java.util.Map;

public class EditNote extends AppCompatActivity {
    EditText title,note,date;
    Button editar,deletar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.title);
        note = findViewById(R.id.note);
        date = findViewById(R.id.date);
        editar = findViewById(R.id.edit);
        deletar =findViewById(R.id.delete);

        String n_title = getIntent().getStringExtra("titulo");
        String n_content = getIntent().getStringExtra("nota");
        String n_data = getIntent().getStringExtra("data");
        final String n_id = getIntent().getStringExtra("id");
        final String n_place = getIntent().getStringExtra("local");
        final String n_author = getIntent().getStringExtra("email");
        title.setText(n_title);
        note.setText(n_content);
        date.setText(n_data);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = title.getText().toString();
                String nota = note.getText().toString();
                String data = date.getText().toString();

                if(!TextUtils.isEmpty(titulo)){
                    updateNote(n_id,titulo,nota,data,n_place,n_author);
                    Intent intent = new Intent(EditNote.this,ListNotes.class);
                    startActivity(intent);
                }
            }
        });
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(n_id);
                dR.removeValue();
                Intent intent = new Intent(EditNote.this,ListNotes.class);
                startActivity(intent);
            }
        });
    }
    private boolean updateNote(final String id, final String title, final String note, final String date,final String place,final String author){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(id);
        Log.v("oi","2");
        Note nota = new Note(id,title,note,place,date,author);
        Log.v("oi","1");
        dR.setValue(nota);
        Toast.makeText(getApplicationContext(),"Note Updated",Toast.LENGTH_LONG).show();
        Log.v("oi","3");
        return true;

    }
}

