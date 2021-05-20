package nnhl.project.ebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import nnhl.project.ebank.Client.ClientMainActivity;
import nnhl.project.ebank.Counsellor.Login.CounsellorLoginActivity;


public class StartScreen extends AppCompatActivity {
    ImageButton counsellerBtn, clientBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startview);
        counsellerBtn = (ImageButton) findViewById(R.id.counsellorButton);
        counsellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, CounsellorLoginActivity.class);
                startActivity(intent);
            }
        });
        clientBtn = (ImageButton) findViewById(R.id.clientButton);
        clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, ClientMainActivity.class);
                startActivity(intent);
            }
        });
    }
}