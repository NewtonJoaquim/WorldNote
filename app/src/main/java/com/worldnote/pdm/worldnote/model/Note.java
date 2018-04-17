package com.worldnote.pdm.worldnote.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by zdsantos on 16/04/18.
 */

public class Note {

    private String title;
    private String content;
    private Location location;
    private Date date;
    private User author;

    public Note() {}

    public Note(String title, String content, Location location, Date date, User author) {
        this.title = title;
        this.content = content;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
