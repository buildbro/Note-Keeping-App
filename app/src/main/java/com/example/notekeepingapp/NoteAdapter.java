package com.example.notekeepingapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.ViewHolder<NoteAdapter.NoteViewHolder> {

    List<Note> mNoteList;


    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
