package nnhl.project.ebank.Counsellor.VideoCall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import nnhl.project.ebank.R;

public class OutgoingActivity extends AppCompatActivity {

    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing);
        initComponents();

        //Gửi FCM rồi
        //Phát âm thanh đổ chuông
        //Đợi nhận FCM.
    }

    private void initComponents() {
        btnCancel=findViewById(R.id.cancelbutton);
    }
}