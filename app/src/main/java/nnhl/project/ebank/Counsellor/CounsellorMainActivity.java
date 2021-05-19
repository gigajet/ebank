package nnhl.project.ebank.Counsellor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import nnhl.project.ebank.R;

public class CounsellorMainActivity extends AppCompatActivity {

    private ImageButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellormainview);
        initComponents();
    }

    private void initComponents() {
        btnLogout=findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounsellorMainActivity.this.finish();
            }
        });
    }
}