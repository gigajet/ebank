package nnhl.project.ebank.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    private void initRequestComponents() {
        edClientName=findViewById(R.id.clientNameEdittext);
        edReqContent=findViewById(R.id.requestContentEdittext);
    }

    public void onclick_back(View view) {
        finish();
    }

    public void onclick_request(View view) {
        presenter.request(edClientName.getText().toString(), edReqContent.getText().toString());
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
    }
}