package com.example.notekeepingapp;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Note {
    public String id;
    public String ownerId;
    public String noteBody;

    @ServerTimestamp
    public Date timeAdded;

    public Note(String ownerId, String noteBody) {
        this.ownerId = ownerId;
        this.noteBody = noteBody;
    }

    public Note() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public Date getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Date timeAdded) {
        this.timeAdded = timeAdded;
    }
}
