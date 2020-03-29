package com.example.notekeepingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private FirebaseAuth mAuth;
    FirebaseFirestore mDb;

    RecyclerView mRecyclerView;
    NoteAdapter mAdapter;
     List<Note> mNoteListArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.note_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NoteAdapter(mNoteListArray, this);
        mRecyclerView.setAdapter(mAdapter);
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();

        readNotes();
    }

    private void readNotes() {

        mDb.collection("Notes")
                .whereEqualTo("ownerId", mAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        mNoteListArray.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            Note note = doc.toObject(Note.class);
                            if (note.getNoteBody() != null) {
                                note.setId(doc.getId());
                                mNoteListArray.add(note);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            gotoLogin();
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_new) {
            startActivity(new Intent(this, AddNoteActivity.class));
        }
        if (item.getItemId() == R.id.logout) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onViewClicked(View v, int position) {
        Note currentNote = mNoteListArray.get(position);
        if (v.getId() == R.id.edit_text_view) {
            Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
            intent.putExtra("id", currentNote.getId());
            intent.putExtra("body", currentNote.getNoteBody());
            startActivity(intent);
            return;
        }
        if (v.getId() == R.id.delete_text_view) {
            deleteNote(currentNote.getId());
        }
    }

    private void deleteNote(String id) {
        mDb.collection("Notes").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Oops!! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
