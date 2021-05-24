package nnhl.project.ebank.Counsellor.EditProfile;

import android.text.Editable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.CounsellorModel;

public class CounsellorEditProfilePresenter {
    CounsellorEditProfileView counsellorEditProfileView;
    FirebaseDatabase firebaseDatabase;
    CounsellorEditProfilePresenter(CounsellorEditProfileView view)
    {
        counsellorEditProfileView = view;
        firebaseDatabase  = FirebaseDatabase.getInstance();

    }
    void EditProfile(String accountName,String userNameChanged, String passwordChanged, String phoneChanged, String emailChanged)
    {
        if( accountName == null) {
            counsellorEditProfileView.ActionNoAccountName();
            return;
        }
        if( userNameChanged.isEmpty() && passwordChanged.isEmpty() && phoneChanged.isEmpty() && emailChanged.isEmpty())
        {
            counsellorEditProfileView.ActionNoDataFilled();
            return;
        }

        Query query = firebaseDatabase.getReference().child(Const.TBL_counsellors).child(accountName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = 5;
                if(snapshot.getValue() != null) {
                    CounsellorModel p = snapshot.getValue(CounsellorModel.class);
                    assert p != null;
                    Map<String, Object> map = new HashMap<>();


                    if( !userNameChanged.isEmpty())
                        map.put("name",userNameChanged);
                    else map.put("name",p.getName());

                    if( !passwordChanged.isEmpty())
                        map.put("password",passwordChanged);
                    else map.put("password",p.getPassword());

                    if(!phoneChanged.isEmpty())
                        map.put("phone",phoneChanged);
                    else map.put("phone",p.getPhone());

                    if(!emailChanged.isEmpty())
                        map.put("email",emailChanged);
                    else map.put("email",p.getEmail());

                    firebaseDatabase.getReference(Const.TBL_counsellors).child(accountName).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            counsellorEditProfileView.ActionUpdateSuccessful();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            counsellorEditProfileView.ActionUpdateFail();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
  
    public interface  CounsellorEditProfileView
    {
        void updateUserName();
        void updatePassWord();
        void updateTelephone();
        void updateEmail();
        void ActionNoDataFilled();
        void ActionNoAccountName();
        void ActionUpdateSuccessful();
        void ActionUpdateFail();
    }

}
