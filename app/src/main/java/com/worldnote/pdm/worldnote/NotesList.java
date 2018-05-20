package com.worldnote.pdm.worldnote;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.worldnote.pdm.worldnote.model.Note;

import java.util.List;

public class NotesList extends ArrayAdapter<Note>{
    private Activity context;
    List<Note> notes;

    public NotesList(Activity context,List<Note> notes){
        super(context,R.layout.layout_note_list,notes);
        this.context = context;
        this.notes = notes;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_note_list,null,true);

        TextView title = listViewItem.findViewById(R.id.title);
        TextView textViewNote = listViewItem.findViewById(R.id.note);
        TextView date = listViewItem.findViewById(R.id.date);
        TextView place = listViewItem.findViewById(R.id.place);

        Note note = notes.get(position);
        title.setText(note.getTitle());
        textViewNote.setText(note.getContent());
        date.setText(note.getDate());
        place.setText(note.getLocation());

        return listViewItem;
    }
}
