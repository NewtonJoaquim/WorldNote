package com.worldnote.pdm.worldnote.model;

import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by zdsantos on 16/04/18.
 */

public class Note {

    private static Note[] n = {
            new Note("01", "Nota 1", "Conteudo da nota 1", "05/05/18", null),
            new Note("02", "Nota 2", "Conteudo da nota 2", "05/05/18", null),
            new Note("03", "Nota 3", "Conteudo da nota 3", "05/05/18", null),
            new Note("04", "Nota 4", "Conteudo da nota 4", "05/05/18", null),
    };

    public static ArrayList<Note> ITEMS = new ArrayList<Note>(Arrays.asList(n));

    private String id;
    private String title;
    private String content;
    private Place location;
    private String date;
    private FirebaseUser author;

    public Note() {}

    public Note(String title, String content, Place location, String date, FirebaseUser author) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
        this.author = author;
    }
    public Note(String id, String title, String content, String date, FirebaseUser author){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
    }
    public Note(String title, String content, String date, FirebaseUser author){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Place getLocation() {
        return location;
    }

    public void setLocation(Place location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FirebaseUser getAuthor() {
        return author;
    }

    public void setAuthor(FirebaseUser author) {
        this.author = author;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}
