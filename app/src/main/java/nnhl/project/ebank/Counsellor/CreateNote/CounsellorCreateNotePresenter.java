package nnhl.project.ebank.Counsellor.CreateNote;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nnhl.project.ebank.Const;


public class CounsellorCreateNotePresenter {
    public interface CounsellorCreateNoteView
    {
        void ActionCreateNoteSuccess();
        void ActionCreateNoteFail();
        void ActionOverlapToken();
        void ActionWrongFormat();
    }

    CounsellorCreateNoteView counsellorCreateNoteView;
    FirebaseDatabase firebaseDatabase;
    CounsellorCreateNotePresenter(CounsellorCreateNoteView view)
    {
        counsellorCreateNoteView = view ;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    void createNoteAndEmail(String clientName, String clientMail, String noteContent)
    {
        if(clientMail.isEmpty() || clientMail.isEmpty() || noteContent.isEmpty())
        {

            return;
        }
        String token = generateString(8);
        firebaseDatabase.getReference(Const.TBL_NOTES).child(token).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists() == false)
                {
                    Map<String, Object> map = new HashMap<>();
                    map.put("clientName", clientName);
                    map.put("clientMail", clientMail);
                    map.put("noteContent", noteContent);
                    map.put("token",token);
                    firebaseDatabase.getReference(Const.TBL_counsellors).child(token).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    counsellorCreateNoteView.ActionCreateNoteSuccess();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                   counsellorCreateNoteView.ActionCreateNoteFail();
                                }
                            });
                }
                else
                {
                    counsellorCreateNoteView.ActionOverlapToken();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public  String generateString(int lenght) {
        String uuid = UUID.randomUUID().toString();
        return  uuid.replace("-", "").substring(0,lenght);
    }
}
