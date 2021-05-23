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

import nnhl.project.ebank.Counsellor.EditProfile.CounsellorEditProfileActivity;
import nnhl.project.ebank.Counsellor.Login.CounsellorLoginActivity;
import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.R;

public class CounsellorMainActivity extends AppCompatActivity implements CounsellorMainPresenter.View {

    private ImageButton btnLogout;
    CounsellorMainPresenter presenter;
    private ImageButton btnCall, btnStart,btnEdit;
    TextView tvRequestContent, tvClientName;
    String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellormainview);
        presenter=new CounsellorMainPresenter(this);
        Intent temp = getIntent();
        account = temp.getStringExtra("Account");
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
                    String jitsi_room=presenter.get_videocall_token();
                    Intent intent=new Intent(CounsellorMainActivity.this, VideoCallActivity.class);
                    intent.putExtra("jitsi_room", presenter.get_videocall_token());
                    startActivity(intent);
                }
            }
        });
        btnEdit = findViewById(R.id.editButton);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CounsellorMainActivity.this, CounsellorEditProfileActivity.class);
                intent.putExtra("AccountName",account);
                startActivity(intent);

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
        tvRequestContent.setText("");
        tvClientName.setText("");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void update_client_info(String client_name, String req_content) {
        tvClientName.setText(client_name);
        tvRequestContent.setText(req_content);
    }
}