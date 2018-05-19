package com.worldnote.pdm.worldnote.provider;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.worldnote.pdm.worldnote.model.Note;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private static final String TAG = "NoteViewModel";

    private final GenericTypeIndicator<List<Note>> TYPE = new GenericTypeIndicator<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("notes");
    MutableLiveData<List<Note>> notes = new MutableLiveData<>();

    public LiveData<List<Note>> getNotes() {
        if (notes.getValue() == null)
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    notes.postValue(toNotes(dataSnapshot));
                } else {
                    Log.d(TAG, "Datasnapshot does not exists: " + dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Database error", databaseError.toException());
            }
        });

        return notes;
    }

    public LiveData<Note> getNote(String id) {
        final MutableLiveData<Note> note = new MutableLiveData<>();

        ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                note.postValue(dataSnapshot.getValue(Note.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Database error", databaseError.toException());
            }
        });

        return note;
    }

    private List<Note> toNotes(DataSnapshot dataSnapshot) {
        return (List<Note>) dataSnapshot.getValue(TYPE);
    }

}
