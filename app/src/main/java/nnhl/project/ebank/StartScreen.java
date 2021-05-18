package nnhl.project.ebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nnhl.project.ebank.Counsellor.Login.CounsellorLoginActivity;


public class StartScreen extends AppCompatActivity {
    Button counsellerBtn;
    Button clientBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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