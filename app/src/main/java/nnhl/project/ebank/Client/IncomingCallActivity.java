package nnhl.project.ebank.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.R;

public class IncomingCallActivity extends AppCompatActivity implements IncomingCallPresenter.View {

    ImageButton btnStart, btnDecline;
    IncomingCallPresenter presenter;
    String TAG="IncomingCallActivity";
    private String videocall_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomingcallview);
        presenter=new IncomingCallPresenter(this);
        initComponents();
    }

    private void initComponents() {
        btnStart=findViewById(R.id.startButton);
        videocall_token = getIntent().getStringExtra(Const.REMOTE_MSG_CALL_TOKEN);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.accept_call(videocall_token);
            }
        });
        btnDecline=findViewById(R.id.declineButton);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deny_call(videocall_token);
            }
        });
    }

    @Override
    public void accept_call_failure_callback(String error) {
        Toast.makeText(this, "Accept failure: "+error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Accept call failure "+error);
    }

    @Override
    public void accept_call_sucess() {
        Intent intent=new Intent(this, VideoCallActivity.class);
        intent.putExtra(Const.TAG_TOKEN_VIDEOCALL, videocall_token);
        startActivity(intent);
    }

    @Override
    public void deny_call_sucess() {
        finish();
    }

    @Override
    public void deny_call_failure_callback(String error) {
        Toast.makeText(this, "Accept failure: "+error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Accept call failure "+error);
    }
}