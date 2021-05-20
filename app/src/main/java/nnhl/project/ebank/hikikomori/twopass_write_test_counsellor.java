package nnhl.project.ebank.hikikomori;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

import nnhl.project.ebank.R;
import java.util.Random;

public class twopass_write_test_counsellor extends AppCompatActivity {
    FirebaseDatabase fb;
    TextView tvResult, tvToBeTransmit;
    Button btnGet;
    String TAG="TWOPASS-COUNSELLOR";
    String client_handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hikikomori_twopass_write_test);
        fb= FirebaseDatabase.getInstance();
        tvResult=findViewById(R.id.tvResult);
        tvToBeTransmit=findViewById(R.id.tvToBeTransmit);
        btnGet=findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long counsellor_id=new Random().nextLong();
                tvToBeTransmit.setText(String.valueOf(counsellor_id));
                fb.getReference().child("waiting_clients").runTransaction(new Transaction.Handler() {
                    @NonNull
                    @NotNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                        long childCount = currentData.getChildrenCount();
                        if (childCount == 0) {
                            Log.d(TAG, "No waiting client");
                            return Transaction.abort();
                        }
                        for (MutableData childData: currentData.getChildren()) { //first child
                            String client_handle = childData.getValue(String.class);
                            if (client_handle == null) {
                                return Transaction.success(currentData);
                            }
                            if (client_handle.charAt(0)=='[') continue;
                            twopass_write_test_counsellor.this.client_handle = client_handle;
                            childData.setValue("["+counsellor_id+"]");
                            return Transaction.success(currentData);
                        }
                        Toast.makeText(twopass_write_test_counsellor.this,"No waiting client",Toast.LENGTH_SHORT).show();
                        return Transaction.abort();
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (!committed)
                            Log.e("TEST-TRANSACTION", "onComplete: "+error);
                        else
                            tvResult.setText(client_handle);
                    }
                });
            }
        });
    }
}