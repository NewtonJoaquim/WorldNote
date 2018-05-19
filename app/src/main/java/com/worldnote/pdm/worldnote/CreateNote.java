package com.worldnote.pdm.worldnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.worldnote.pdm.worldnote.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNote extends AppCompatActivity {
    TextView placeText;
    EditText tittle,note,date;
    Button pickaplace,confirm;
    int PLACE_PICKER_REQUEST=1;
    Place p;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);

        placeText = findViewById(R.id.getplace);
        tittle = findViewById(R.id.tittle);
        note = findViewById(R.id.note);
        confirm = findViewById(R.id.create);
        pickaplace = findViewById(R.id.pickaplace);
        date = findViewById(R.id.date);

        mDatabase = FirebaseDatabase.getInstance().getReference("notes");
        mAuth = FirebaseAuth.getInstance();

        pickaplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder= new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    Log.v("oi","1");
                    Context c = getApplicationContext();
                    //intent = builder.build(getApplicationContext());
                    startActivityForResult(builder.build(CreateNote.this),PLACE_PICKER_REQUEST);
                    Log.v("oi","2");
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode,int resultcode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultcode == RESULT_OK){
                 p = PlacePicker.getPlace(data,this);
                String adress = String.format("Place %s",p.getAddress());
                placeText.setText(adress);
            }
        }
    }
    private void addNote(){
        String id  = mDatabase.push().getKey();
        String n_tittle = tittle.getText().toString();
        String n_note = note.getText().toString();
        FirebaseUser us = mAuth.getCurrentUser();
        String n_date = date.getText().toString();
        Log.v("teste","1");
        Note n = new Note(n_tittle,n_note,n_date,us);
        Log.v("teste","2");
        mDatabase.child(id).setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    task.getException();
                }
            }
        });
        Log.v("teste","3");
        Toast.makeText(CreateNote.this,"Note added",Toast.LENGTH_SHORT).show();
    }
}


