package nnhl.project.ebank.Counsellor.VideoCall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Global;
import nnhl.project.ebank.R;

public class OutgoingActivity extends AppCompatActivity {

    ImageButton btnDecline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outcomingcallview);
        initComponents();

        //Phát âm thanh đổ chuông
        //Đợi nhận FCM.
    }

    @Override
    protected void onResume() {
        super.onResume();
        String dochuong = (String) Global.getInstance().getData().get(Const.TAG_WAITING_FOR_CALL);
        if (dochuong == null || dochuong.equals(Const.TAG_NO))
            finish();
        else ;
    }

    private void initComponents() {
        btnDecline=findViewById(R.id.declineButton);
        //TODO We assume that counsellor never cancel the call
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO trả kết quả đã decline cuộc gọi.
                finish();
            }
        });
    }
}