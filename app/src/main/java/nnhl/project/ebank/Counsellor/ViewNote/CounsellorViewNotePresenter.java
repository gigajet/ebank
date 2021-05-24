package nnhl.project.ebank.Counsellor.ViewNote;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.CounsellorModel;
import nnhl.project.ebank.Counsellor.Login.CounsellorLoginPresenter;
import nnhl.project.ebank.Counsellor.NoteModel;

public class CounsellorViewNotePresenter {
    private CounsellorViewNoteView counsellorViewNoteView;
    FirebaseDatabase firebase;


    CounsellorViewNotePresenter(CounsellorViewNoteView v)
    {
        counsellorViewNoteView = v;
        firebase = FirebaseDatabase.getInstance(Const.DB_URL);
    }
    void viewNote(String token)
    {

        if (token.isEmpty())
        {
            counsellorViewNoteView.ActionFail();
            return ;
        }
        Query query = firebase.getReference().child(Const.TBL_NOTES).child(token);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    snapshot.getKey();
                    NoteModel p = snapshot.getValue(NoteModel.class);

                    counsellorViewNoteView.ActionSuccess(token, p.getClientName(), p.getClientMail(),p.getNoteContent());

                }
                else counsellorViewNoteView.ActionFail();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                counsellorViewNoteView.ActionFail();
            }
        });
    }


    public interface CounsellorViewNoteView
    {
        public void ActionSuccess(String token, String clientName,String clientMail, String noteContent);
        public void ActionFail();

    }
}
