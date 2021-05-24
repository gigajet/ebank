package nnhl.project.ebank.Counsellor.ViewNote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import nnhl.project.ebank.R;
public class CounsellorViewNoteActivity extends AppCompatActivity implements CounsellorViewNotePresenter.CounsellorViewNoteView {
    EditText tokenEditText;
    TextView noteContentEditText;
    ImageButton viewNoteBtn;
    CounsellorViewNotePresenter counsellorViewNotePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellorsearchfileview);
        tokenEditText = findViewById(R.id.fileCodeEdittext);
        noteContentEditText = findViewById(R.id.viewSearchNoteTextView);
        viewNoteBtn = findViewById(R.id.searchButton);
        counsellorViewNotePresenter = new CounsellorViewNotePresenter(this);
        viewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counsellorViewNotePresenter.viewNote(tokenEditText.getText().toString());
            }
        });
    }

    @Override
    public void ActionSuccess(String token, String clientName, String clientMail, String noteContent) {
        Toast.makeText(CounsellorViewNoteActivity.this,"View note successful",Toast.LENGTH_LONG).show();
        tokenEditText.setText(token);
        noteContentEditText.setText("Dear Mr/Mrs "+ clientName +"\n" + "Gmail: " +clientMail + "\n" + noteContent);

    }

    @Override
    public void ActionFail() {
        Toast.makeText(CounsellorViewNoteActivity.this,"View note failed",Toast.LENGTH_LONG).show();
    }
}