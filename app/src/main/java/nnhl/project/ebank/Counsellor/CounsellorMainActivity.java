package nnhl.project.ebank.Counsellor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import nnhl.project.ebank.Counsellor.VideoCall.OutgoingActivity;
import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.R;

public class CounsellorMainActivity extends AppCompatActivity implements CounsellorMainPresenter.View {

    private ImageButton btnLogout;
    CounsellorMainPresenter presenter;
    private ImageButton btnCall, btnStart;
    TextView tvRequestContent, tvClientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellormainview);
        presenter=new CounsellorMainPresenter(this);
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
        btnCall=findViewById(R.id.callButton);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!presenter.is_ready_to_call()) {
                    Toast.makeText(CounsellorMainActivity.this, "Client not available to call",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    //Call code
                    /*
                    String jitsi_room=presenter.get_videocall_token();
                    Intent intent=new Intent(CounsellorMainActivity.this, VideoCallActivity.class);
                    intent.putExtra("jitsi_room", presenter.get_videocall_token());
                    startActivity(intent);
                     */
                    presenter.call();
                }
            }
        });
        tvRequestContent=findViewById(R.id.requestContentTextview);
        tvClientName=findViewById(R.id.clientNameTextview);
        btnStart=findViewById(R.id.startButton);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClientName.setText("Getting info...");
                tvRequestContent.setText("Getting info...");
                presenter.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        update_client_info("", "");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void update_client_info(String client_name, String req_content) {
        tvClientName.setText(client_name);
        tvRequestContent.setText(req_content);
    }

    @Override
    public void call_failure(String error_msg) {
        Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void call_sucess() {
        Intent intent=new Intent(this, OutgoingActivity.class);
        intent.putExtra("jitsi_room", presenter.get_videocall_token());
        intent.putExtra("client_fcm", presenter.get_client_fcm_token());
        startActivity(intent);
    }
}