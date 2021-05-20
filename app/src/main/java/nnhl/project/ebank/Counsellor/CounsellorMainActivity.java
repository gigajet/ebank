package nnhl.project.ebank.Counsellor;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
                    //Call
                }
            }
        });
        btnStart=findViewById(R.id.startButton);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });
        tvRequestContent=findViewById(R.id.requestContentTextview);
        tvClientName=findViewById(R.id.clientNameTextview);
    }

    @Override
    public void update_client_info(String client_name, String req_content) {
        tvClientName.setText(client_name);
        tvRequestContent.setText(req_content);
    }
}