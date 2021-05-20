package nnhl.project.ebank.hikikomori;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.R;

/*
    Scheme: Two queues "waiting_clients" and "waiting_counsellors"

    if other queue is null
        add self to queue, then listen to change
    else
        attempt to communicate to first available by twopass-write

 */

public class twoqueue_client extends AppCompatActivity {

    FirebaseDatabase fb;
    TextView tvResult, tvToBeTransmit;
    Button btnGet;
    String TAG="TWOQUEUE-CLIENT";
    String other_handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hikikomori_twopass_write_test);
        fb=FirebaseDatabase.getInstance(Const.DB_URL);
        tvResult=findViewById(R.id.tvResult);
        tvToBeTransmit=findViewById(R.id.tvToBeTransmit);
        btnGet=findViewById(R.id.btnGet);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long myhandle=(new Random().nextLong())%(1L<<63);
                tvToBeTransmit.setText(String.valueOf(myhandle));
                tvResult.setText("Please wait...");
                DatabaseReference dbref=fb.getReference("volatile");
                dbref.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @NotNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                        long nrootchild=currentData.getChildrenCount();
                        if (nrootchild == 0) { //first time so null (we have data_leg so child never be 0)
                            //try to write dummy value, server will reject
                            //and run this again
                            currentData.child("bin_aqsa").setValue("wawahmu");
                        }
                        else {
                            for (MutableData data : currentData.child("waiting_counsellors").getChildren()) {
                                String tmp=data.getValue(String.class);
                                if (tmp.charAt(0)=='[') continue;
                                other_handle=tmp;
                                tvResult.setText(other_handle);
                                data.setValue("["+String.valueOf(myhandle)+"]");
                                return Transaction.success(currentData);
                            }

                            //no waiting client to be paired
                            long handle=currentData.child("next_handle").getValue(Long.class);
                            currentData.child("next_handle").setValue(handle+1);
                            currentData.child("waiting_clients")
                                    .child(String.valueOf(handle))
                                    .setValue(String.valueOf(myhandle));
                            DatabaseReference ref=fb.getReference("volatile")
                                    .child("waiting_clients")
                                    .child(String.valueOf(handle));
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    String enclosed=snapshot.getValue(String.class);
                                    if (enclosed==null || enclosed.charAt(0)!='[') return;
                                    other_handle=enclosed.substring(1,enclosed.length()-1);
                                    tvResult.setText(other_handle);
                                    ref.removeValue();
                                    ref.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                    Log.w(TAG, "loadPost:onCancelled", error.toException());
                                }
                            });
                            return Transaction.success(currentData);
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, boolean committed, @Nullable @org.jetbrains.annotations.Nullable DataSnapshot currentData) {
                        if (!committed)
                            Log.e("TEST-TRANSACTION", "onComplete: "+error);
                    }
                },false);
            }
        });
    }
}