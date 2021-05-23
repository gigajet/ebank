package nnhl.project.ebank.Client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.Global;
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

    @Override
    protected void onResume() {
        super.onResume();
        /*
        String nenthoatra = (String) Global.getInstance().getData().get(Const.TAG_INCOMING_STAY);
        if (nenthoatra == null || nenthoatra.equals(Const.TAG_NO)) {
            Global.getInstance().getData().put(Const.TAG_INCOMING_STAY,Const.TAG_YES);
            finish();
        }
        else ;
         */
    }

    private BroadcastReceiver callResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type=intent.getStringExtra(Const.REMOTE_MSG_CALL_RESPONSED);
            if (type !=null) {
                if (type.equals(Const.REMOTE_MSG_CANCEL_CALL)) {
                    finish(); //jitsi will run another place
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                callResponseReceiver,
                new IntentFilter(Const.REMOTE_MSG_CALL_RESPONSED));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(callResponseReceiver);
        super.onStop();
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
        startActivityForResult(intent, 333);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 333) {
            Intent intent = new Intent(Const.TAG_CALL_COMPLETE);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            finish();
        }
    }

    @Override
    public void deny_call_sucess() {
        Intent intent = new Intent(Const.TAG_CALL_COMPLETE);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        finish();
    }

    @Override
    public void deny_call_failure_callback(String error) {
        Toast.makeText(this, "Accept failure: "+error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Accept call failure "+error);
    }
}