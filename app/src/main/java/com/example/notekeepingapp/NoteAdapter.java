package com.example.notekeepingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    List<Note> mNoteList;
    RecyclerViewClickListener mListener;

    public NoteAdapter(List<Note> mNoteList, RecyclerViewClickListener mListener) {
        this.mNoteList = mNoteList;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.noteBodyTextView.setText(note.getNoteBody());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteBodyTextView, editBtnTextView, deleteBtnTextView;

        public NoteViewHolder(@NonNull View itemView, final RecyclerViewClickListener listener) {
            super(itemView);
            noteBodyTextView = itemView.findViewById(R.id.note_body_text_view);
            editBtnTextView = itemView.findViewById(R.id.edit_text_view);
            deleteBtnTextView = itemView.findViewById(R.id.delete_text_view);

            editBtnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onViewClicked(view, getAdapterPosition());
                    }
                }
            });

            deleteBtnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onViewClicked(view, getAdapterPosition());
                    }
                }
            });
        }
    }
}
