package com.worldnote.pdm.worldnote.model;

import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by zdsantos on 16/04/18.
 */

public class Note {

    private String title;
    private String content;
    private String location;
    private String date;
    private String author;

    public Note() {}

    public Note(String title, String content, String location, String date, String author) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
        this.author = author;
    }
    public Note(String title, String content, String date, String author){
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
