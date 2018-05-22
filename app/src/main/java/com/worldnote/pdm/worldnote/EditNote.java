package com.worldnote.pdm.worldnote;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
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
    TextView place;
    Button editar,deletar,pick;
    int PLACE_PICKER_REQUEST=1;
    Place p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.title);
        note = findViewById(R.id.note);
        date = findViewById(R.id.date);
        editar = findViewById(R.id.edit);
        deletar =findViewById(R.id.delete);
        place = findViewById(R.id.place);
        pick = findViewById(R.id.pick);

        String n_title = getIntent().getStringExtra("titulo");
        String n_content = getIntent().getStringExtra("nota");
        String n_data = getIntent().getStringExtra("data");
        final String n_id = getIntent().getStringExtra("id");
        final String n_place = getIntent().getStringExtra("local");
        final String n_author = getIntent().getStringExtra("email");
        title.setText(n_title);
        note.setText(n_content);
        date.setText(n_data);
        place.setText(n_place);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = title.getText().toString();
                String nota = note.getText().toString();
                String data = date.getText().toString();
                String local = place.getText().toString();

                if(!TextUtils.isEmpty(titulo)){
                    updateNote(n_id,titulo,nota,data,local,n_author);
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

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder= new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    Context c = getApplicationContext();
                    //intent = builder.build(getApplicationContext());
                    startActivityForResult(builder.build(EditNote.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
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
    protected void onActivityResult(int requestCode,int resultcode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultcode == RESULT_OK){
                p = PlacePicker.getPlace(data,this);
                String adress = String.format("Place %s",p.getName());
                place.setText(adress);
            }
        }
    }
}

