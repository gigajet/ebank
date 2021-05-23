package nnhl.project.ebank.Counsellor.VideoCall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Global;
import nnhl.project.ebank.R;
import nnhl.project.ebank.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingActivity extends AppCompatActivity {

    private static final String TAG = "FCM";
    ImageButton btnDecline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outcomingcallview);
        initComponents();

        //Phát âm thanh đổ chuông
        //Đợi nhận FCM.
    }

    private BroadcastReceiver callResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type=intent.getStringExtra(Const.REMOTE_MSG_CALL_RESPONSED);
            if (type !=null) {
                if (type.equals(Const.REMOTE_MSG_ACCEPT_CALL)) {
                    finish(); //jitsi will run another place
                }
                else if (type.equals(Const.REMOTE_MSG_DENY_CALL)) {
                    finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        /*
        String dochuong = (String) Global.getInstance().getData().get(Const.TAG_WAITING_FOR_CALL);
        if (dochuong == null || dochuong.equals(Const.TAG_NO))
            finish();
        else ;
         */
    }

    private void initComponents() {
        btnDecline=findViewById(R.id.declineButton);
        //TODO We assume that counsellor never cancel the call
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callback<String> callback = new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //do nothing.
                        Log.d("FCM", "I counsellor cancel the call");
                        finish();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "sendRemoteMessage callback onFailure: "+t.getMessage());
                    }
                };
                try {
                    JSONArray tokens=new JSONArray();
                    tokens.put(Global.getInstance().get_fcm_token());
                    JSONObject data=new JSONObject();
                    JSONObject body=new JSONObject();
                    data.put(Const.REMOTE_MSG_TYPE, Const.REMOTE_MSG_CANCEL_CALL);
                    body.put(Const.REMOTE_MSG_DATA, data);
                    body.put(Const.REMOTE_MSG_REGISTRATION_IDS, tokens);
                    Util.sendRemoteMessage(body.toString(), callback);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}