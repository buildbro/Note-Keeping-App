package com.example.notekeepingapp;

public class Note {
    public String id;
    public String ownerId;
    public String noteBody;

    public Note(String id, String ownerId, String noteBody) {
        this.id = id;
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
}
