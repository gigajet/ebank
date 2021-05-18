package nnhl.project.ebank;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CounsellorRegisterPresenter implements ICounsellorRegisterPresenter {

    ICounsellorRegisterView view;
    FirebaseDatabase firebase;
    DatabaseReference data;

    public CounsellorRegisterPresenter(ICounsellorRegisterView view) {
        this.view = view;
        firebase=FirebaseDatabase.getInstance(Const.DB_URL);
    }

    @Override
    public void register(String username, String name, String password, String email, String phone, String SID) {
        //firebase operations
        firebase.getReference(Const.TBL_SID).child(SID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists() == false) { //SID not exists
                    register_callback(-1, "SID not exists");
                }
                else {
                    firebase.getReference(Const.TBL_counsellors).child(username).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists() == true) {
                                register_callback(-1, "Username existed");
                            }
                            else {
                                //Registered or not is not checked
                                //New register account with username will replace old account
                                Map<String, Object> map = new HashMap<>();
                                map.put("name", name);
                                map.put("email", email);
                                map.put("password", password);
                                map.put("phone",phone);
                                firebase.getReference(Const.TBL_counsellors).child(username).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                register_callback(0, "Sucess");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                register_callback(-1, e.getMessage());
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            register_callback(-1, "Read cancelled");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                register_callback(-1, "Read cancelled");
            }
        });
    }

    public void register_callback (int status, String msg) {
        Log.d("REGISTER", "CALLBACK");
        switch (status) {
            case 0: view.register_sucessful(); break;
            case 1: view.register_failed("Unknown error"); break;
            default:
                view.register_failed(msg);
        }
    }
}
