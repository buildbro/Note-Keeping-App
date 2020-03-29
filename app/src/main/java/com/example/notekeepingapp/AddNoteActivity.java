package com.example.notekeepingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class AddNoteActivity extends AppCompatActivity {

    EditText noteEditText;
    Button addNoteBtn;

    FirebaseFirestore mDb;
    FirebaseAuth mAuth;
    String id, body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            body = intent.getStringExtra("body");
        }
        initializeUi();
    }

    private void initializeUi() {
        noteEditText = findViewById(R.id.note_body_edit_text);
        addNoteBtn = findViewById(R.id.add_note_button);

        if (!TextUtils.isEmpty(body)) {
            noteEditText.setText(body);
        }

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(id)) {
                    updateNote();
                } else {
                    saveNote();
                }

            }
        });
    }

    private void updateNote() {
        DocumentReference noteRef = mDb.collection("Notes").document(id);
        noteRef
                .update("noteBody", noteEditText.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNoteActivity.this, "Note Updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNoteActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveNote() {
        String noteBody = noteEditText.getText().toString();

        Note note = new Note(mAuth.getCurrentUser().getUid(), noteBody);

        mDb.collection("Notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddNoteActivity.this, "Note Added!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNoteActivity.this, "Oops!! something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
