package com.worldnote.pdm.worldnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class CreateNote extends AppCompatActivity {
    TextView getText;
    Button b;
    int PLACE_PICKER_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);

        getText = findViewById(R.id.getplace);
        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
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

    }
    @Override
    protected void onActivityResult(int requestCode,int resultcode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultcode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,this);
                String adress = String.format("Place %s",place.getAddress());
                getText.setText(adress);
            }
        }
    }
}
