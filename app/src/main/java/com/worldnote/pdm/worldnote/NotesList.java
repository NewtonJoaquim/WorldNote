package com.worldnote.pdm.worldnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.worldnote.pdm.worldnote.model.Note;

import java.util.List;

public class NotesList extends ArrayAdapter<Note>{
    private Activity context;
    List<Note> notes;
    ImageButton btnDelete;

    public NotesList(Activity context,List<Note> notes){
        super(context,R.layout.layout_note_list,notes);
        this.context = context;
        this.notes = notes;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_note_list,null,true);

        TextView title = listViewItem.findViewById(R.id.title);
        TextView textViewNote = listViewItem.findViewById(R.id.note);
        TextView date = listViewItem.findViewById(R.id.date);
        TextView place = listViewItem.findViewById(R.id.place);

        final Note note = notes.get(position);
        title.setText(note.getTitle());
        textViewNote.setText(note.getContent());
        date.setText(note.getDate());
        place.setText(note.getLocation());

        btnDelete = listViewItem.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.delete_text)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(note.getNoteId());
                                dR.removeValue();
                                notes.remove(position);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }
                        });
                builder.create().show();
            }
        });

        return listViewItem;
    }
}
