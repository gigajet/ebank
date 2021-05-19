package nnhl.project.ebank.hikikomori;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import nnhl.project.ebank.R;

public class twopass_write_test_counsellor extends AppCompatActivity {
    FirebaseDatabase fb;
    TextView tvResult;
    Button btnGet;
    String TAG="TWOPASS-COUNSELLOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hikikomori_twopass_write_test);
        fb= FirebaseDatabase.getInstance();
        tvResult=findViewById(R.id.tvResult);
        btnGet=findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("counsellor");
            }
        });
    }
}