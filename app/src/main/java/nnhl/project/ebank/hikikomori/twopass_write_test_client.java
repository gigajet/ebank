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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import nnhl.project.ebank.Const;
import java.util.Random;
import nnhl.project.ebank.R;

/*
    The plan:
    1. Client put himself (its handle) into waiting_clients and wait for any change of that key
    --waiting_clients:
        |
        |-- client1_handle
        |-- client2_handle
        |-- client3_handle
    2. Counsellor make a transaction read the first waiting_client that is not enclosed inside
    [] (i.e square bracket).
    3. Counsellor reads the client handle, replace existing data with
    [counsellor's id] then commit transaction. (enclosed in sq bracket)
    4. Client read the counsellor id inside sq bracket.
   What for?

   We try to call transaction to firebase to test whether it supports solving concurrency problem.
 */

public class twopass_write_test_client extends AppCompatActivity {

    FirebaseDatabase fb;
    TextView tvResult;
    Button btnGet;
    String TAG="TWOPASS-CLIENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hikikomori_twopass_write_test);
        fb=FirebaseDatabase.getInstance();
        tvResult=findViewById(R.id.tvResult);
        btnGet=findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("Please wait...");
                int id=new Random().nextInt();
                long handle = new Random().nextLong();
                DatabaseReference ref=fb.getReference("waiting_clients");
                fb.getReference("waiting_clients").child(String.valueOf(id))
                        .setValue(String.valueOf(handle))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                fb.getReference("waiting_clients").child(String.valueOf(id))
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                String enclosed_counsellor_id=snapshot.getValue(String.class);
                                                String counsellor_id=enclosed_counsellor_id.substring(1,enclosed_counsellor_id.length()-2);
                                                tvResult.setText(counsellor_id);
                                                //Delete the key
                                                fb.getReference("waiting_clients").child(String.valueOf(id)).setValue(null);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                Log.w(TAG, "loadPost:onCancelled", error.toException());
                                            }
                                        });
                            }
                        });
            }
        });
    }

}

