package nnhl.project.ebank.Counsellor.CreateNote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nnhl.project.ebank.R;
public class CounsellorCreateNoteActivity extends AppCompatActivity implements CounsellorCreateNotePresenter.CounsellorCreateNoteView {
    EditText clienEditText, noteEditText, emailEditText;
    ImageButton createFileBtn;
    CounsellorCreateNotePresenter counsellorCreateNotePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellorcreatenoteview);
        init();
    }

    void init()
    {
        counsellorCreateNotePresenter = new CounsellorCreateNotePresenter(CounsellorCreateNoteActivity.this);
        clienEditText = findViewById(R.id.clientNameNote);
        noteEditText = findViewById(R.id.requestContentNote);
        emailEditText = findViewById(R.id.emailNote);
        createFileBtn = findViewById(R.id.createFileButton);

        createFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counsellorCreateNotePresenter.createNoteAndEmail(clienEditText.getText().toString(), emailEditText.getText().toString(), noteEditText.getText().toString());

            }
        });

    }

    @Override
    public void ActionCreateNoteSuccess() {
        Toast.makeText(CounsellorCreateNoteActivity.this, "Create note successful", Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { emailEditText.getText().toString() });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Dear Mr/Mrs " + clienEditText.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, noteEditText.getText().toString());
        intent.setType("message/rfc822");
        startActivity(intent);

    }

    @Override
    public void ActionCreateNoteFail() {
        Toast.makeText(CounsellorCreateNoteActivity.this, "Create note failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ActionOverlapToken() {
        Toast.makeText(CounsellorCreateNoteActivity.this, "Overlap token", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ActionWrongFormat() {
        Toast.makeText(CounsellorCreateNoteActivity.this, "Wrong format, please fill in every field before create a new note", Toast.LENGTH_SHORT).show();
    }
}