package nnhl.project.ebank;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class CounsellorRegisterPresenter implements ICounsellorRegisterPresenter {

    ICounsellorRegisterView view;
    FirebaseDatabase firebase;
    DatabaseReference data;

    public CounsellorRegisterPresenter(ICounsellorRegisterView view) {
        this.view = view;
        firebase=FirebaseDatabase.getInstance();
    }

    @Override
    public void register(String name, String email, String phone, String SID) {
        Log.d("REGISTER", "REGISTER");

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d("REGISTER", "connected");
                } else {
                    Log.d("REGISTER", "not connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("REGISTER", "Listener was cancelled");
            }
        });

        //firebase operations
        firebase.getReference().setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                register_callback(0, ""); //mock code to test
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                register_callback(-1, e.getMessage());
                Log.e("REGISTER", e.getMessage());
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
