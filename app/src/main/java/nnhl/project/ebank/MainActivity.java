package nnhl.project.ebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int a  = 5;
        int b = 5;
        Intent intent = new Intent(this, CounsellorRegisterActivity.class);
        startActivity(intent);
    }
}
