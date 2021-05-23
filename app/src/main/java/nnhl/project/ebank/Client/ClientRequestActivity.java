package nnhl.project.ebank.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.CounsellorMainActivity;
import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.Global;
import nnhl.project.ebank.R;
public class ClientRequestActivity extends AppCompatActivity implements ClientRequestPresenter.View {
    EditText edClientName,edReqContent;
    ClientRequestPresenter presenter;
    String TAG="CLIENT-REQUEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientrequestview);
        presenter=new ClientRequestPresenter(this);
        initRequestComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String cumback = (String) Global.getInstance().getData().get(Const.TAG_JUST_FINISH_CALL);
        if (cumback == null || cumback.equals(Const.TAG_NO)) ;
        else {
            edClientName.setText(""); edReqContent.setText("");
            setContentView(R.layout.clientrequestview);
            initRequestComponents();
            Global.getInstance().getData().put(Const.TAG_JUST_FINISH_CALL, Const.TAG_NO);
        }
    }

    private void initRequestComponents() {
        edClientName=findViewById(R.id.clientNameEdittext);
        edReqContent=findViewById(R.id.requestContentEdittext);
    }

    public void onclick_back(View view) {
        finish();
    }

    public void onclick_request(View view) {
        presenter.request(edClientName.getText().toString(), edReqContent.getText().toString(),
                Global.getInstance().get_fcm_token());
        setContentView(R.layout.clientwaitingview);
    }

    public void onclick_endrequest(View view) {
        //End request here...
        setContentView(R.layout.clientrequestview);
        initRequestComponents();
    }

    @Override
    public void fetch_callback(String videocall_token) {
        Log.d(TAG,"Jitsi token: "+videocall_token);
        /*
        Intent intent=new Intent(ClientRequestActivity.this, VideoCallActivity.class);
        intent.putExtra("jitsi_room", videocall_token);
        startActivity(intent);
         */
    }
}