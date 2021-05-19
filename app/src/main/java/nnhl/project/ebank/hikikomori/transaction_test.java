package nnhl.project.ebank.hikikomori;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.R;

/*
    The plan:
    First we create a database with following info:

    --waiting_clients:
        |
        |-- client1_handle
        |-- client2_handle
        |-- client3_handle
   What for?

   We try to call transaction to firebase to test whether it supports solving concurrency problem.
 */

public class transaction_test extends AppCompatActivity {

    FirebaseDatabase fb;
    TextView tvClient;
    String assigned_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hikikomori_transaction_test);
        fb=FirebaseDatabase.getInstance(Const.DB_URL);
        tvClient=findViewById(R.id.tvAssignedClient);
    }

    public void toast (String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onclick_start(View view) {
        fb.getReference().child("waiting_clients").runTransaction(new Transaction.Handler() {
            @NonNull
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                long childCount = currentData.getChildrenCount();
                if (childCount == 0) {
                    toast("No waiting client");
                    return Transaction.abort();
                }
                for (MutableData childData: currentData.getChildren()) { //first child
                    String client_handle = childData.getValue(String.class);
                    if (client_handle == null) {
                        return Transaction.success(currentData);
                    }
                    assigned_client = client_handle;
                    childData.setValue(null); //Delete after get
                    return Transaction.success(currentData);
                }
                return Transaction.abort();
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (!committed)
                    Log.e("TEST-TRANSACTION", "onComplete: "+error);
                else
                    tvClient.setText(assigned_client);
            }
        });
    }
}