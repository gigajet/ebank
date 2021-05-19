package nnhl.project.ebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nnhl.project.ebank.Counsellor.Login.CounsellorLoginActivity;
import nnhl.project.ebank.hikikomori.fbmsg_test;
import nnhl.project.ebank.hikikomori.transaction_test;


public class StartScreen extends AppCompatActivity {
    Button counsellerBtn;
    Button clientBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(StartScreen.this, fbmsg_test.class);
        startActivity(intent);
        finish();

        setContentView(R.layout.activity_start_screen);
        counsellerBtn = (Button) findViewById(R.id.Counsellor);
        counsellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, CounsellorLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}